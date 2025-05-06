package kr.co.pawong.pwbe.favorites.presentation.dto.response;

import kr.co.pawong.pwbe.adoption.application.service.dto.response.AdoptionCard;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FavoritesListResponse {
    private final List<AdoptionCard> favoritesList;
}
