package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("HUGGINGFACE_EMBEDDING")
@RequiredArgsConstructor
public class HuggingFaceEmbeddingAdapter implements EmbeddingProcessorPort {

    private final HuggingFaceEmbeddingModel embeddingModel;

    @Override
    public float[] embed(String completion) {
        return embeddingModel.embed(completion);
    }
}
