package kr.co.pawong.pwbe.user.infrastructure.repository;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
  private final UserJpaRepository userJpaRepository;

  @Override
  public User findByUserId(Long userId) {
    return userJpaRepository.findByUserId(userId)
        .map(UserEntity::toDomain)
        .orElseThrow(() ->
            new IllegalArgumentException("User not found")
        );
  }

  @Override
  public User findByUserSocialId(Long socialId) {
    return userJpaRepository.findBySocialId(socialId)
        .map(UserEntity::toDomain)
        .orElse(null);
  }
}
