package kr.co.pawong.pwbe.adoption.infrastructure.adapter.openai;

import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("OPENAI_PROMPT")
@RequiredArgsConstructor
public class OpenAiPromptAdapter implements PromptProcessorPort {

    private final OpenAiChatModel chatModel;

    @Override
    public String refineByPrompt(String prompt) {
        return chatModel.call(prompt);
    }
}