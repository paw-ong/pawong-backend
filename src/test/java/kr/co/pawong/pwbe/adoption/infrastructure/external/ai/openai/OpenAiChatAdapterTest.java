package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.openai;

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
    void AI_채팅_확인() {
        String output = openAiChatAdapter.refineByFeature("새끼 강아지인데 어르신들과 잘 지내고 순종적인");
        System.out.println(output);
    }
}