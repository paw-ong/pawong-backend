package kr.co.pawong.pwbe.adoption.infrastructure.adapter.openai;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("AI 연동 테스트는 전체 빌드 시 제외")
@SpringBootTest(properties = "spring.profiles.active=dev")
class OpenAiChatAdapterTest {

    @Autowired
    private OpenAiChatAdapter openAiChatAdapter;

    @Test
    void 질의_되나_확인() {
        String output = openAiChatAdapter.refineByPrompt("너 모델 이름 뭐야");
        System.out.println(output);
    }
}