package kr.co.pawong.pwbe.adoption.fake;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;

public class FakeChatAdapter implements ChatProcessorPort {
    @Override
    public String refineByFeature(String prompt) {
        return "test";
    }
}
