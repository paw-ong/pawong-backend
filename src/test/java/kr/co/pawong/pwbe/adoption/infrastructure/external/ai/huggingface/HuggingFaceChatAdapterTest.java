package kr.co.pawong.pwbe.adoption.infrastructure.external.ai.huggingface;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("AI 연동 테스트는 전체 빌드 시 제외")
@SpringBootTest(properties = "spring.profiles.active=dev")
class HuggingFaceChatAdapterTest {

    @Autowired
    private HuggingFaceChatAdapter chatAdapter;

    @Test
    void AI_채팅_확인() {
        String output = chatAdapter.refineByFeature("상냥하고 아주 멋진 동물이야. 근데 좀 아픈 것 같기도..");
        System.out.println(output);
    }
}