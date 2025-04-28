package kr.co.pawong.pwbe.adoption.fake;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;

import java.util.List;

public class FakeChatAdapter implements ChatProcessorPort {
    @Override
    public String queryByPrompt(String prompt) {
        return "AI 대답";
    }

    @Override
    public String refineAdoptionSentence(String sentence) {
        return "test";
    }

    @Override
    public List<String> getTagsByFeature(String feature) {
        return List.of("정이많음", "사람을좋아함");
    }
}

