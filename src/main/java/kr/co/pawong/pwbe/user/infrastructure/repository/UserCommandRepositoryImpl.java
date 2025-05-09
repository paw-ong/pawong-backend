package kr.co.pawong.pwbe.user.infrastructure.repository;

import static kr.co.pawong.pwbe.global.error.errorcode.CustomErrorCode.USER_NOT_FOUND;

import kr.co.pawong.pwbe.global.error.exception.BaseException;
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
                        new BaseException(USER_NOT_FOUND)
                )
                .updateProfile(user)
                .toDomain();
    }
}
