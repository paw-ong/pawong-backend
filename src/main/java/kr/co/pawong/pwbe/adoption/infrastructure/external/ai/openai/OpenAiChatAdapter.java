package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.openai;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums.SystemMessage.*;

/**
 * 사용자 입력(프롬프트)을 기반으로 GPT 응답을 생성하는 기능을 제공합니다.
 */
@Component
@Qualifier("OPENAI_CHAT")
@RequiredArgsConstructor
public class OpenAiChatAdapter implements ChatProcessorPort {

    private final OpenAiChatModel chatModel;

    @Override
    public String queryByPrompt(String prompt) {
        return "";
    }

    @Override
    public String refineAdoptionSentence(String sentence) {
        return "";
    }

    @Override
    public List<String> getTagsByFeature(String feature) {
        return List.of();
    }
}