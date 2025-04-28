package kr.co.pawong.pwbe.adoption.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.ChatProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.port.EmbeddingProcessorPort;
import kr.co.pawong.pwbe.adoption.application.service.util.AdoptionAiExecutor;
import kr.co.pawong.pwbe.adoption.fake.FakeEmbeddingAdapter;
import kr.co.pawong.pwbe.adoption.fake.FakeChatAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executor;

class AdoptionAiServiceImplTest {
    private AdoptionAiServiceImpl service;

    @BeforeEach
    void setUp() {
        // 동기 실행용 executor
        Executor syncExec = Runnable::run;
        AdoptionAiExecutor executor = new AdoptionAiExecutor(syncExec);

        // fake adapter 준비
        ChatProcessorPort fakeChat = new FakeChatAdapter();
        EmbeddingProcessorPort fakeEmb = new FakeEmbeddingAdapter();

        service = new AdoptionAiServiceImpl(fakeEmb, fakeChat, executor);
    }

    /** embed */
    @Test
    void 임베딩_정상_요청() {
        // Given
        String input = "test";
        // When
        float[] output = service.embed(input);
        // Then
        Assertions.assertThat(output).isEqualTo(new float[]{1.01f, 1.02f});
    }

    @Test
    void 임베딩에_null이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = null;
        // When & Then
        Assertions.assertThatThrownBy(() -> service.embed(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임베딩에_빈_문자열이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.embed(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임베딩에_공백이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "    \n   ";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.embed(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /** refine */
    @Test
    void 문자열_정제_정상_요청() {
        // Given
        String input = "test";
        // When
        String output = service.refineSearchCondition(input);
        // Then
        Assertions.assertThat(output).isEqualTo(input);
    }

    @Test
    void 문자열_정제에_null이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = null;
        // When & Then
        Assertions.assertThatThrownBy(() -> service.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열_정제에_빈_문자열이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 문자열_정제에_공백이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "  \n  ";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.refineSearchCondition(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    /** tag */
    @Test
    void 태깅_정상_요청() {
        // Given
        String input = "test";
        // When
        List<String> output = service.tag(input);
        // Then
        Assertions.assertThat(output).isEqualTo(List.of("정이많음", "사람을좋아함"));
    }

    @Test
    void 태깅_시에_null이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = null;
        // When & Then
        Assertions.assertThatThrownBy(() -> service.tag(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 태깅_시에_빈_문자열이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.tag(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 태깅_시에_공백이_입력된_경우_IllegalArgumentException을_던짐() {
        // Given
        String input = "  \n  ";
        // When & Then
        Assertions.assertThatThrownBy(() -> service.tag(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

}