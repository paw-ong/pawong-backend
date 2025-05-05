package kr.co.pawong.pwbe.favorites.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesResponse {
    private final boolean isInFavorites;
}