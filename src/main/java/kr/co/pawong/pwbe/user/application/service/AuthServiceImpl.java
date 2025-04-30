package kr.co.pawong.pwbe.user.application.service;

import jakarta.transaction.Transactional;
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
  private final UserQueryRepository userQueryRepository;
  private final UserCommandRepository userCommandRepository;

  @Transactional
  @Override
  public AuthResponse signUp(Long userId, UserUpdate userUpdate) {
    User pendingUser = userQueryRepository.findByUserId(userId);
    User updatedUser = userCommandRepository.update(pendingUser.update(userUpdate));
    return new AuthResponse(
        updatedUser.getUserId(),
        updatedUser.getStatus()
    );
  }
}
