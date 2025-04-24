package kr.co.pawong.pwbe.adoption.infrastructure.adapter.huggingface;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @reference: https://huggingface.co/docs/inference-providers/tasks/chat-completion
 * @implements: ChatModel - 스프링 AI 라이브러리에서 만들어 놓은 채팅 전용 인터페이스입니다.
 * 이를 구현해서 HuggingFace Inference API 기반의 채팅 모델을 만들었습니다.
 * 사용자 메시지를 기반으로 AI 모델의 응답을 생성하는 기능을 제공합니다.
 */
@RequiredArgsConstructor
public class HuggingFaceChatModel implements ChatModel {

    private final WebClient webClient;     // baseUrl="https://router.huggingface.co/hf-inference"
    private final String modelName;        // ex) "Qwen/QwQ-32B"
    private final ChatOptions defaultOptions = ChatOptions.builder().maxTokens(500).build();

    @Override
    public ChatResponse call(Prompt prompt) {
        // 1) Prompt → HF messages 포맷으로 변환
        List<Map<String, String>> messages = prompt.getInstructions().stream()
                .map(m -> Map.of(
                        "role",    m.getMessageType().getValue(),
                        "content", m.getContent()           // getContent() deprecated 경고 있으면 m.getText() 로 교체
                ))
                .toList();

        // 2) 요청 바디 구성 (스펙 기반)
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("messages",   messages);
        body.put("max_tokens", defaultOptions.getMaxTokens());
        body.put("stream",     false);

        // 3) HF Inference API 호출
        JsonNode resp = webClient.post()
                .uri("/models/{model}/v1/chat/completions", modelName)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        // 4) 스펙에 맞춰 안전하게 파싱
        JsonNode choices = resp.path("choices");
        List<Generation> gens = new ArrayList<>();
        if (choices.isArray()) {
            for (JsonNode choice : choices) {
                String content = choice
                        .path("message")
                        .path("content")
                        .asText("");
                AssistantMessage msg = new AssistantMessage(content);
                gens.add(new Generation(msg));
            }
        }

        // 5) ChatResponse 생성
        return ChatResponse.builder()
                .generations(gens)
                .build();
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return defaultOptions;
    }
}