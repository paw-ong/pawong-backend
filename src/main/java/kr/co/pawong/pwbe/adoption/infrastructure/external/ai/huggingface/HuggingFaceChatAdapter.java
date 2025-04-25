package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("HUGGINGFACE_CHAT")
@RequiredArgsConstructor
public class HuggingFaceChatAdapter implements ChatProcessorPort {

    private final HuggingFaceChatModel chatModel;

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
