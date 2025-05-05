package kr.co.pawong.pwbe.favorites.application.service.port;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    List<Favorites> findAllByUserId(Long userId);

    Optional<Favorites> findByUserIdAndAdoptionId(Long userId, Long adoptionId);

    void save(Favorites favorites);

    void deleteById(Long id);
}
