package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserCreate;
import kr.co.pawong.pwbe.user.application.domain.UserUpdate;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;

public interface AuthService {
  User createOrGetUser(UserCreate userCreate);
  AuthResponse signUp(Long userId, UserUpdate userUpdate);
}
