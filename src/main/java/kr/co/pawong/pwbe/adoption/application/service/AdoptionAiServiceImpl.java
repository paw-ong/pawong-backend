package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @exception: null이나 빈 문자열, 공백이 입력된 경우 IllegalArgumentException을 던집니다.
 */
@Service
@RequiredArgsConstructor
public class AdoptionAiServiceImpl implements AdoptionAiService {

    private final EmbeddingProcessorPort embeddingPort;
    private final PromptProcessorPort promptPort;

    // 문장 정제하는 함수
    @Override
    public String refineSearchCondition(String searchTerm) {
        validateNotBlank(searchTerm);
        return promptPort.refineByPrompt(searchTerm);
    }

    // 문장 입베딩하는 함수
    @Override
    public float[] embed(String completion) {
        validateNotBlank(completion);
        return embeddingPort.embed(completion);
    }

    // 입력값 검증하는 함수
    private void validateNotBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be null or blank.");
        }
    }

}
