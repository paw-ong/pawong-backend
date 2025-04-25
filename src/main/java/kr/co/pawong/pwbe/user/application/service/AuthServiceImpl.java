package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserUpdate;
import kr.co.pawong.pwbe.user.application.service.port.UserCommandRepository;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
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
  private final UserQueryRepository userQueryRepository;
  private final UserCommandRepository userCommandRepository;

  @Override
  public AuthResponse kakaoLogin(String code) {
    User loginUser = kakaoService.login(code);
    return new AuthResponse(
        jwtTokenProvider.generateJwtToken(loginUser.getUserId()),
        loginUser.getUserId(),
        loginUser.getStatus());
  }

  @Override
  public AuthResponse signUp(Long userId, UserUpdate userUpdate) {
    User pendingUser = userQueryRepository.findByUserId(userId);
    User updatedUser = userCommandRepository.update(pendingUser.update(userUpdate));
    return new AuthResponse(
        jwtTokenProvider.generateJwtToken(updatedUser.getUserId()),
        updatedUser.getUserId(),
        updatedUser.getStatus()
    );
  }
}
