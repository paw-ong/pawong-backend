package kr.co.pawong.pwbe.user.application.service.port;

import kr.co.pawong.pwbe.user.application.domain.User;

public interface UserCommandRepository {
  User save(User user);
  User updateProfile(User user);
}
