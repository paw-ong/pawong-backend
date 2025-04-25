package kr.co.pawong.pwbe.user.infrastructure.repository;

import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findBySocialId(Long socialId);
}
