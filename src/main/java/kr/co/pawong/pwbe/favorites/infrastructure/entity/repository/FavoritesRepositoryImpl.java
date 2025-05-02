package kr.co.pawong.pwbe.favorites.infrastructure.entity.repository;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.port.FavoritesRepository;
import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import kr.co.pawong.pwbe.favorites.infrastructure.entity.FavoritesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {
    private final FavoritesJpaRepository favoritesJpaRepository;

    @Override
    public List<Favorites> findAllByUserId(Long userId) {
        return favoritesJpaRepository.findAllByUserId(userId).stream()
                .map(FavoritesEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Favorites> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, FavoriteTargetType type) {
        return Optional.empty();
    }

    @Override
    public void save(Favorites favorites) {
        favoritesJpaRepository.save(favorites);
    }

    public void deleteById(Long id) {
        favoritesJpaRepository.deleteById(id);
    }
}
