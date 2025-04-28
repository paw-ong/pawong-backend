package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Qualifier("HUGGINGFACE_EMBEDDING")
@RequiredArgsConstructor
public class HuggingFaceEmbeddingAdapter implements EmbeddingProcessorPort {

    private final HuggingFaceEmbeddingModel embeddingModel;

    @Override
    public @Nullable float[] embed(String completion) {
        try {
            return embeddingModel.embed(completion);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
