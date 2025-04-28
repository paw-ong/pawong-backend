package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.service.port.KakaoAuthPort;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserCommandService;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

  private final UserQueryService userQueryService;
  private final UserCommandService userCommandService;

  @Override
  public User createOrGetUser(KakaoUserResponse kakaoUserInfo) {
    User getUser = userQueryService.getUserBySocialId(kakaoUserInfo.id);
    if(getUser == null) {
      return userCommandService.createUser(kakaoUserInfo.toUserCreate());
    }
    return getUser;
  }
}
