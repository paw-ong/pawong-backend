package kr.co.pawong.pwbe.adoption.infrastructure.external;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * infrastructure라.. 스프링 부트 없이 테스트가 안 될 것 같아요. 비활성화 해놓겠습니다.
 */
@Disabled("OpenAI 연동 테스트는 전체 빌드 시 제외")
@SpringBootTest(properties = "spring.profiles.active=dev")
class OpenAiEmbeddingAdapterTest {

    @Autowired
    private OpenAiEmbeddingAdapter openAiEmbeddingAdapter;    // ← 생성자 대신 여기에 @Autowired

    @Test
    void 임베딩_되나_확인() {
        float[] output = openAiEmbeddingAdapter.embed("테스트입니다.");
        System.out.println(Arrays.toString(output));
    }
}