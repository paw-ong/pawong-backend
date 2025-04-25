package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @exception: null이나 빈 문자열, 공백이 입력된 경우 IllegalArgumentException을 던집니다.
 */
@Service
public class AdoptionAiServiceImpl implements AdoptionAiService {

    private final EmbeddingProcessorPort embeddingPort;
    private final ChatProcessorPort chatPort;

    // 문장 정제하는 함수
    @Override
    public String refineSearchCondition(String searchTerm) {
        validateNotBlank(searchTerm);
        return chatPort.refineByFeature(searchTerm);
    }

    // 문장 임베딩하는 함수
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


    // 임베딩할 때 사용할 구현체 설정
    private final String EMBEDDING_BEAN_NAME = "OPENAI_EMBEDDING";
    // AI 채팅할 때 사용할 구현체 설정
    private final String CHAT_BEAN_NAME = "OPENAI_CHAT";

    public AdoptionAiServiceImpl(
            @Qualifier(EMBEDDING_BEAN_NAME) EmbeddingProcessorPort embeddingPort,
            @Qualifier(CHAT_BEAN_NAME) ChatProcessorPort chatPort
    ) {
        this.embeddingPort = embeddingPort;
        this.chatPort = chatPort;
    }

}
