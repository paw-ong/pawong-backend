package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.service.port.KakaoAuthPort;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {
  private final KakaoAuthPort kakaoAuthPort;

  @Override
  public AuthResponse login(String code) {
    // 카카오로부터 User Info를 가져온다.
    KakaoUserResponse kakaoUserInfo = kakaoAuthPort.getKakaoUserInfo(code);
    // dummy jwt
    String jwtToken = "aaaaaaaaa";
    return new AuthResponse(jwtToken, jwtToken);
  }
}
