package kr.co.pawong.pwbe.adoption.infrastructure.external;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("OPENAI_EMBEDDING")
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingProcessorPort {

    private final EmbeddingModel embeddingModel;

    @Override
    public float[] embed(String completion) {
        return embeddingModel.embed(completion);
    }
}
