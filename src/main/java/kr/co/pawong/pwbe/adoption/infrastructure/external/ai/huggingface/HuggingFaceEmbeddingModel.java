package kr.co.pawong.pwbe.adoption.infrastructure.adapter.huggingface;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @reference: https://huggingface.co/docs/inference-providers/tasks/feature-extraction
 * @implements: EmbeddingModel - 스프링 AI 라이브러리에서 만들어 놓은 임베딩 전용 인터페이스입니다.
 * 이를 구현해서 HuggingFace Interface API 기반의 임베딩 모델을 만들었습니다.
 * 문장에서 특징을 추출(임베딩)하는 기능을 제공합니다.
 */
@RequiredArgsConstructor
public class HuggingFaceEmbeddingModel implements EmbeddingModel {
    private final WebClient webClient;
    private final String modelName;

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        List<float[]> resp = webClient.post()
                .uri("/pipeline/feature-extraction/{model}", modelName)
                .bodyValue(request.getInstructions())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<float[]>>() {})
                .block();

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