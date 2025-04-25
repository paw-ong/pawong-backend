package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @reference: https://huggingface.co/docs/inference-providers/tasks/feature-extraction
 * @implements: EmbeddingModel - 스프링 AI 라이브러리에서 만들어 놓은 임베딩 전용 인터페이스입니다.
 * 이를 구현해서 HuggingFace Interface API 기반의 임베딩 모델을 만들었습니다.
 * 문장에서 특징을 추출(임베딩)하는 기능을 제공합니다.
 */
@Slf4j
@RequiredArgsConstructor
public class HuggingFaceEmbeddingModel implements EmbeddingModel {

    private final RetryTemplate retryTemplate;
    private final WebClient webClient;
    private final String modelName;

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<float[]> resp = retryTemplate.execute(
                (ctx) -> {
                    log.info("HuggingFace 임베딩 요청 시도 {}회차", ctx.getRetryCount() + 1);
                    return webClient.post()
                            .uri("/pipeline/feature-extraction/{model}", modelName)
                            .bodyValue(request.getInstructions())
                            .retrieve()
                            .bodyToMono(new ParameterizedTypeReference<List<float[]>>() {
                            })
                            .block();
                },
                (ctx) -> {
                    Throwable lastError = ctx.getLastThrowable();
                    log.error("HuggingFace 임베딩 요청 {}회 모두 실패했습니다. 마지막 오류: {}",
                            ctx.getRetryCount(),
                            lastError != null ? lastError.getMessage() : "없음");
                    return List.of();
                }
        );

        List<Embedding> embeddings = new ArrayList<>();
        if (resp != null) {
            for (int i = 0; i < resp.size(); i++) {
                embeddings.add(new Embedding(resp.get(i), i));
            }
        }
        return new EmbeddingResponse(embeddings);
    }

    @Override
    public float[] embed(Document document) {
        return embed(document.getContent());
    }
}