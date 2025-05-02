package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.application.service.port.FavoritesRepository;
import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import kr.co.pawong.pwbe.favorites.enums.LikeStatus;
import kr.co.pawong.pwbe.favorites.presentation.dto.response.FavoritesResponse;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceImplTest {

    @InjectMocks
    private FavoritesServiceImpl favoritesService;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private UserQueryRepository userQueryRepository;

    @Test
    void 이미_찜을_했다면_다시_찜을_할_수_없음() {
        // given
        Long userId = 1L;
        Long targetId = 100L;
        FavoriteTargetType targetType = FavoriteTargetType.ADOPTION;

        FavoritesRequest request = FavoritesRequest.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(targetType)
                .build();

        UserEntity user = UserEntity.builder().userId(userId).build();

        when(userQueryRepository.findByUserId(userId)).thenReturn(user.toDomain());
        when(favoritesRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType))
                .thenReturn(Optional.empty());

        // when
        FavoritesResponse response = favoritesService.like(request);

        // then
        assertEquals(LikeStatus.LIKED, response.getStatus());
    }

    @Test
    void 찜을_한_상태에서_찜_요청을_보내면_찜은_취소됨() {
        // given
        Long userId = 1L;
        Long targetId = 100L;
        FavoriteTargetType targetType = FavoriteTargetType.ADOPTION;

        FavoritesRequest request = FavoritesRequest.builder()
                .userId(userId)
                .targetId(targetId)
                .targetType(targetType)
                .build();

        UserEntity user = UserEntity.builder().userId(userId).build();
        Favorites existingFavorite = Favorites.builder()
                .id(10L)
                .userId(userId)
                .targetId(targetId)
                .targetType(targetType)
                .build();

        when(userQueryRepository.findByUserId(userId)).thenReturn(user.toDomain());
        when(favoritesRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType))
                .thenReturn(Optional.of(existingFavorite));

        // when
        FavoritesResponse response = favoritesService.like(request);

        // then
        assertEquals(LikeStatus.UNLIKED, response.getStatus());
    }
}
