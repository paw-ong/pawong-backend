package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.openai;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 문장에서 특징을 추출(임베딩)하는 기능을 제공합니다.
 */
@Component
@Qualifier("OPENAI_EMBEDDING")
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingProcessorPort {

    private final OpenAiEmbeddingModel embeddingModel;

    @Override
    public float[] embed(String completion) {
        return embeddingModel.embed(completion);
    }
}
