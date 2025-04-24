package kr.co.pawong.pwbe.adoption.infrastructure.adapter.huggingface;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Disabled("AI 연동 테스트는 전체 빌드 시 제외")
@SpringBootTest(properties = "spring.profiles.active=dev")
class HuggingFaceEmbeddingAdapterTest {

    @Autowired
    private HuggingFaceEmbeddingAdapter embeddingAdapter;

    @Test
    void 임베딩_되나_확인() {
        float[] output = embeddingAdapter.embed("테스트입니다.");
        System.out.println("벡터 차원: " + output.length);
        System.out.println("벡터: " + Arrays.toString(output));
    }
}