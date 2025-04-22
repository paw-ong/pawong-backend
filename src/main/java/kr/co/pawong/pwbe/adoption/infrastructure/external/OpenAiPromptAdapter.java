package kr.co.pawong.pwbe.adoption.infrastructure.external;

import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("OPENAI_PROMPT")
@RequiredArgsConstructor
public class OpenAiPromptAdapter implements PromptProcessorPort {

    private final ChatClient.Builder chatClientBuilder;

    @Override
    public String refineByPrompt(String prompt) {
        ChatClient client = chatClientBuilder.build();

        return client.prompt(prompt)
                .call()
                .content();
    }
}