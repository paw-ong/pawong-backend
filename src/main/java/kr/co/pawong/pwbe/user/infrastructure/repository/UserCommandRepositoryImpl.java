package kr.co.pawong.pwbe.user.infrastructure.repository;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.service.port.UserCommandRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public User save(User user) {
    return userJpaRepository.save(UserEntity.of(user))
        .toDomain();
  }

  @Override
  public User updateProfile(User user) {
    return userJpaRepository.findByUserId(user.getUserId())
        .orElseThrow(() ->
            new IllegalArgumentException("User not found")
        )
        .updateProfile(user)
        .toDomain();
  }
}
