package kr.co.pawong.pwbe.favorites.presentation.dto.response;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FavoritesListResponse {
    private final List<Favorites> favoritesList;
}
