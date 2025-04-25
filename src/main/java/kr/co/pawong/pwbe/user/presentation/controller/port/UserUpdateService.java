package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserCreate;

public interface UserUpdateService {
  User createUser(UserCreate userCreate);
}
