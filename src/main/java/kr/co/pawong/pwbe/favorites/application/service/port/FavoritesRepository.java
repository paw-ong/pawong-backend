package kr.co.pawong.pwbe.favorites.application.service.port;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository {
    // TODO: findAllByUserIdOrThrow (UserRepository 구현 완료된 후에, userExists 검증)
    List<Favorites> findAllByUserId(Long userId);

    Optional<Favorites> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, FavoriteTargetType type);

    void save(Favorites favorites);

    void deleteById(Long id);
}
