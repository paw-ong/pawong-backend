package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.application.domain.User;

public interface UserQueryService {
  User getUser(Long userId);
  User getUserBySocialId(Long socialId);

}
