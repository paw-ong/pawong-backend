package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.infrastructure.security.JwtTokenProvider;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.AuthService;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final KakaoService kakaoService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public AuthResponse kakaoLogin(String code) {
    User loginUser = kakaoService.login(code);
    return new AuthResponse(
        jwtTokenProvider.getJwtToken(loginUser.getUserId()),
        loginUser.getUserId(),
        loginUser.getStatus());
  }
}
