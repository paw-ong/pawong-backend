package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;

import java.util.List;

public interface FavoritesService {
    boolean toggleFavorite(FavoritesRequest request);
    boolean checkFavoriteStatus(FavoritesRequest request);
    List<Favorites> findAllByUserId(Long userId);
}
