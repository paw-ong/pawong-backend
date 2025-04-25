package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.fake.FakeEmbeddingAdapter;
import kr.co.pawong.pwbe.adoption.fake.FakeChatAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AdoptionAiServiceImplTest {

    private final AdoptionAiService adoptionAiService =
            new AdoptionAiServiceImpl(
                    new FakeEmbeddingAdapter(),
                    new FakeChatAdapter()
            );

    @Test
    void 임베딩_정상_요청() {
        // Given
        String input = "test";
        // When
        float[] output = adoptionAiService.embed(input);
        // Then
        Assertions.assertThat(output).isEqualTo(new float[]{1.01f, 1.02f});
    }

    @Test
    void 임베딩에_null이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = null;
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.embed(input))
                        .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임베딩에_빈_문자열이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "";
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.embed(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임베딩에_공백이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "    \n   ";
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.embed(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열_정제_정상_요청() {
        // Given
        String input = "test";
        // When
        String output = adoptionAiService.refineSearchCondition("test");
        // Then
        Assertions.assertThat(output).isEqualTo("test");
    }

    @Test
    void 문자열_정제에_null이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = null;
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열_정제에_빈_문자열이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "";
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열_정제에_공백이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "  \n  ";
        // When & Then
        Assertions.assertThatThrownBy(() -> adoptionAiService.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}