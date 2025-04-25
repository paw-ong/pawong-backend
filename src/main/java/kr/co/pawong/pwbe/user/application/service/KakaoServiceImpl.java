package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserCreate;
import kr.co.pawong.pwbe.user.application.service.port.KakaoAuthPort;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import kr.co.pawong.pwbe.user.infrastructure.repository.UserJpaRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoServiceImpl implements KakaoService {

  private final UserJpaRepository userJpaRepository;
  private final UserUpdateService userUpdateService;
  private final KakaoAuthPort kakaoAuthPort;

  @Override
  public User login(String code) {
    return createOrGetUser(kakaoAuthPort.getKakaoUserInfo(code));
  }

  public User createOrGetUser(KakaoUserResponse kakaoUserInfo) {
    UserEntity userEntity = userJpaRepository.findBySocialId(kakaoUserInfo.id);
    if (userEntity == null) {
      return userUpdateService.createUser(kakaoUserInfo.toUserCreate());
    }
    return userEntity.toDomain();
  }
}
