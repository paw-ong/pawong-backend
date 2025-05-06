package kr.co.pawong.pwbe.favorites.infrastructure.entity.repository;

import kr.co.pawong.pwbe.favorites.infrastructure.entity.FavoritesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesJpaRepository extends JpaRepository<FavoritesEntity, Long> {
    List<FavoritesEntity> findAllByUser_UserId(Long userId);
    Optional<FavoritesEntity> findByUser_UserIdAndAdoption_AdoptionId(Long userId, Long adoptionId);
}