package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums.AnimalFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums.RefineMsg.REFINE_TEMPLATE_1;
import static kr.co.pawong.pwbe.adoption.infrastructure.external.ai.enums.TaggingMsg.TAGGING_TEMPLATE_1;

@Slf4j
@Component
@Qualifier("HUGGINGFACE_CHAT")
@RequiredArgsConstructor
public class HuggingFaceChatAdapter implements ChatProcessorPort {

    private final HuggingFaceChatModel chatModel;

    @Override
    public String queryByPrompt(String prompt) {
        return chatModel.call(prompt);
    }

    @Override
    public String refineAdoptionSentence(String sentence) {
        String refinedStr = chatModel.call(REFINE_TEMPLATE_1.getMessage(sentence));
        log.info("응답 문자열: {}", refinedStr);
        return refinedStr;
    }

    @Override
    public List<String> getTagsByFeature(String feature) {
        String tagsStr = chatModel.call(TAGGING_TEMPLATE_1.getMessage(feature));
        log.info("응답 문자열: {}", tagsStr);
        return AnimalFeature.extractValidTags(tagsStr);
    }
}
