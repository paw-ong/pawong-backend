package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.presentation.dto.response.FavoritesResponse;

public interface FavoritesService {
    FavoritesResponse like(FavoritesRequest request);
}
