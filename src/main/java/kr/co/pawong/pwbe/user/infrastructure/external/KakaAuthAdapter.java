package kr.co.pawong.pwbe.user.infrastructure.external;

import java.util.Objects;
import kr.co.pawong.pwbe.user.application.service.port.KakaoAuthPort;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoTokenResponse;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class KakaAuthAdapter implements KakaoAuthPort {

  private final WebClient webClient;

  @Value("${auth.kakao.redirect-url}")
  private String KAKAO_REDIRECT_URL;
  @Value("${auth.kakao.token-url}")
  private String KAKAO_TOKEN_URL;
  @Value("${auth.kakao.user-info-url}")
  private String KAKAO_API_URL;
  @Value("${auth.kakao.client-id}")
  private String kakaoClientId;

  public String getKakaoAccessToken(String code) {
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type",   "authorization_code");
    form.add("client_id",    kakaoClientId);
    form.add("redirect_uri", KAKAO_REDIRECT_URL);
    form.add("code",         code);

    KakaoTokenResponse resp = webClient.post()
        .uri(KAKAO_TOKEN_URL)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData(form))
        .retrieve()
        .bodyToMono(KakaoTokenResponse.class)
        .block();
    return Objects.requireNonNull(resp).getAccess_token();
  }

  @Override
  public KakaoUserResponse getKakaoUserInfo(String code) {
    return webClient.get()
        .uri(KAKAO_API_URL)
        .headers(h -> h.setBearerAuth(getKakaoAccessToken(code)))
        .retrieve()
        .bodyToMono(KakaoUserResponse.class)
        .block();
  }
}
