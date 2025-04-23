package kr.co.pawong.pwbe.adoption.infrastructure.external;

import kr.co.pawong.pwbe.adoption.infrastructure.adapter.openai.OpenAiPromptAdapter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("OpenAI 연동 테스트는 전체 빌드 시 제외")
@SpringBootTest(properties = "spring.profiles.active=dev")
class OpenAiPromptAdapterTest {

    @Autowired
    private OpenAiPromptAdapter openAiPromptAdapter;

    @Test
    void 질의_되나_확인() {
        String output = openAiPromptAdapter.refineByPrompt("너 모델 이름 뭐야");
        System.out.println(output);
    }
}