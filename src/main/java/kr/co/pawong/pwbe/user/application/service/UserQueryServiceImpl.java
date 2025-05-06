package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
  private final UserQueryRepository userQueryRepository;

  @Override
  public User getUser(Long userId) {
    return userQueryRepository.findByUserIdOrThrow(userId);
  }

  @Override
  public User getUserBySocialId(Long socialId) {
    return userQueryRepository.findByUserSocialId(socialId);
  }
}
