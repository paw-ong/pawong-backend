package kr.co.pawong.pwbe.adoption.infrastructure.adapter.openai;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

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
