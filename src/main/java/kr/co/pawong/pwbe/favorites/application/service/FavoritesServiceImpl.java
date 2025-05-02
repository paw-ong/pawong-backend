package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.application.service.port.FavoritesRepository;
import kr.co.pawong.pwbe.favorites.enums.LikeStatus;
import kr.co.pawong.pwbe.favorites.infrastructure.entity.FavoritesEntity;
import kr.co.pawong.pwbe.favorites.presentation.dto.response.FavoritesResponse;
import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.UserJpaRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService{
    private final FavoritesRepository favoritesRepository;
    private final UserQueryRepository userRepository;

    // toggle 방식 찜
    @Override
    public FavoritesResponse like(FavoritesRequest request) {
        User user = userRepository.findByUserId(request.getUserId());

        Optional<Favorites> existing = favoritesRepository
                .findByUserIdAndTargetIdAndTargetType(
                        request.getUserId(),
                        request.getTargetId(),
                        request.getTargetType());
        LikeStatus status;

        // 이미 찜을 한 경우에는 찜 취소
        if (existing.isPresent()) {
            favoritesRepository.deleteById(existing.get().getId());
            status = LikeStatus.UNLIKED;
        } else {    // 찜을 하지 않은 경우에는 찜
            FavoritesEntity entity = FavoritesEntity.of(UserEntity.of(user), request.getTargetId(), request.getTargetType());
            favoritesRepository.save(entity.toDomain());
            status = LikeStatus.LIKED;
        }

        return FavoritesResponse.builder()
                .userId(request.getUserId())
                .targetId(request.getTargetId())
                .targetType(request.getTargetType())
                .status(status)
                .build();
    }

}
