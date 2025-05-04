package kr.co.pawong.pwbe.user.application.service;

import jakarta.transaction.Transactional;
import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserCreate;
import kr.co.pawong.pwbe.user.application.domain.UserUpdate;
import kr.co.pawong.pwbe.user.application.service.port.UserCommandRepository;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  private final UserQueryRepository userQueryRepository;
  private final UserCommandRepository userCommandRepository;

  @Override
  public User createOrGetUser(UserCreate userCreate) {
    User getUser =  userQueryRepository.findByUserSocialId(userCreate.getSocialId());
    if(getUser == null) {
      return userCommandRepository.save(userCreate.toDomain());
    }
    return getUser;
  }

  @Transactional
  @Override
  public AuthResponse signUp(Long userId, UserUpdate userUpdate) {
    User pendingUser = userQueryRepository.findByUserId(userId);
    User updatedUser = userCommandRepository.updateProfile(pendingUser.update(userUpdate));
    return new AuthResponse(
        updatedUser.getUserId(),
        updatedUser.getStatus()
    );
  }
}
