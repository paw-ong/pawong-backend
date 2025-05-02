package kr.co.pawong.pwbe.favorites.application.service.dto;

import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesRequest {
    Long userId;
    Long targetId;
    FavoriteTargetType targetType;
}
