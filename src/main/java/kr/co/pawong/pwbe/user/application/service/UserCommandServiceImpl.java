package kr.co.pawong.pwbe.user.application.service;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.domain.UserCreate;
import kr.co.pawong.pwbe.user.application.service.port.UserCommandRepository;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
  private final UserCommandRepository userCommandRepository;

  @Transactional
  public User createUser(UserCreate userCreate) {
    return userCommandRepository.save(userCreate.toDomain());
  }
}
