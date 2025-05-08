package kr.co.pawong.pwbe.user.infrastructure.repository;

import static kr.co.pawong.pwbe.global.error.errorcode.CustomErrorCode.USER_NOT_FOUND;
import kr.co.pawong.pwbe.global.error.exception.BaseException;
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
    public User findByUserIdOrThrow(Long userId) {
        return userJpaRepository.findByUserId(userId)
                .map(UserEntity::toDomain)
                .orElseThrow(() ->
                                new BaseException(USER_NOT_FOUND)
                );
    }

    @Override
    public User findByUserSocialId(Long socialId) {
        return userJpaRepository.findBySocialId(socialId)
                .map(UserEntity::toDomain)
                .orElse(null);
    }
}
