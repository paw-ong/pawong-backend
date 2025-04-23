package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionAiServiceImpl implements AdoptionAiService {

    private final EmbeddingProcessorPort embeddingPort;
    private final PromptProcessorPort promptPort;

    @Override
    public String refineSearchCondition(String searchTerm) {
        validateNotBlank(searchTerm);
        return promptPort.refineByPrompt(searchTerm);
    }

    @Override
    public float[] embed(String completion) {
        validateNotBlank(completion);
        return embeddingPort.embed(completion);
    }

    private void validateNotBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank.");
        }
    }

}
