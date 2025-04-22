package kr.co.pawong.pwbe.adoption.infrastructure.external;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("OPENAI_EMBEDDING")
@RequiredArgsConstructor
public class OpenAiEmbeddingAdapter implements EmbeddingProcessorPort {

    private final OpenAiApi openAiApi;

    @Value("${spring.ai.openai.embedding-model-name}")
    private String embeddingModelName;

    @Override
    public float[] embed(String completion) {
        OpenAiEmbeddingModel openAiEmbeddingModel = new OpenAiEmbeddingModel(
                this.openAiApi,
                MetadataMode.EMBED,
                OpenAiEmbeddingOptions.builder()
                        .model(embeddingModelName)
                        .user("user-6")
                        .build(),
                RetryUtils.DEFAULT_RETRY_TEMPLATE);

        EmbeddingResponse embeddingResponse = openAiEmbeddingModel
                .embedForResponse(List.of(completion));

        return embeddingResponse.getResults().getFirst().getOutput();
    }
}
