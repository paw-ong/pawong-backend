package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("HUGGINGFACE_CHAT")
@RequiredArgsConstructor
public class HuggingFaceChatAdapter implements ChatProcessorPort {

    private final HuggingFaceChatModel chatModel;

    @Override
    public String refineByFeature(String feature) {
        return chatModel.call(feature);
    }
}
