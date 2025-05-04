package kr.co.pawong.pwbe.user.application.service.port;

import kr.co.pawong.pwbe.user.application.domain.User;

public interface UserQueryRepository {
  User findByUserId(Long userId);
  User findByUserSocialId(Long socialId);
}
