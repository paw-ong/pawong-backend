package kr.co.pawong.pwbe.adoption.fake;

import kr.co.pawong.pwbe.adoption.application.service.port.PromptProcessorPort;

public class FakePromptAdapter implements PromptProcessorPort {
    @Override
    public String refineByPrompt(String prompt) {
        return "test";
    }
}
