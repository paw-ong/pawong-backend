package kr.co.pawong.pwbe.favorites.presentation.dto.response;

import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import kr.co.pawong.pwbe.favorites.enums.LikeStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse {
    private Long userId;
    private Long targetId;
    private FavoriteTargetType targetType;
    private LikeStatus status;
}