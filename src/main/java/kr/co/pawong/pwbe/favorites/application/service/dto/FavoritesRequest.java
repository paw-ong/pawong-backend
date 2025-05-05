package kr.co.pawong.pwbe.favorites.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoritesRequest {
    Long userId;
    Long adoptionId;
}
