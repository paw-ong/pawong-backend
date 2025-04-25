package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.application.domain.UserUpdate;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;

public interface AuthService {
  AuthResponse kakaoLogin(String code);
  AuthResponse signUp(Long userId, UserUpdate userUpdate);
}
