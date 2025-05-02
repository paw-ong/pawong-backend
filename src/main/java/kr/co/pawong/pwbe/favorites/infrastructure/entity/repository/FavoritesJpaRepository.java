package kr.co.pawong.pwbe.favorites.infrastructure.entity.repository;

import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.infrastructure.entity.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritesJpaRepository extends JpaRepository<Favorites, Long> {
    List<FavoritesEntity> findAllByUserId(Long userId);
}