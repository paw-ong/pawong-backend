package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.presentation.dto.response.FavoritesListResponse;

public interface FavoritesService {
    boolean toggleFavorite(FavoritesRequest request);
    boolean checkFavoriteStatus(FavoritesRequest request);
    FavoritesListResponse findAllByUserId(Long userId);
}
