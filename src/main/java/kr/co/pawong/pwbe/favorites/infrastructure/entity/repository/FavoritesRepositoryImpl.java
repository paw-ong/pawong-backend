package kr.co.pawong.pwbe.favorites.infrastructure.entity.repository;

import kr.co.pawong.pwbe.adoption.infrastructure.repository.AdoptionJpaRepository;
import kr.co.pawong.pwbe.adoption.infrastructure.repository.entity.AdoptionEntity;
import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.port.FavoritesRepository;
import kr.co.pawong.pwbe.favorites.infrastructure.entity.FavoritesEntity;
import kr.co.pawong.pwbe.user.infrastructure.repository.UserJpaRepository;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final FavoritesJpaRepository favoritesJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final AdoptionJpaRepository adoptionJpaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Favorites> findAllByUserId(Long userId) {
        return favoritesJpaRepository
                .findAllByUser_UserId(userId)
                .stream()
                .map(FavoritesEntity::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Favorites> findByUserIdAndAdoptionId(Long userId, Long adoptionId) {
        return favoritesJpaRepository
                .findByUser_UserIdAndAdoption_AdoptionId(userId, adoptionId)
                .map(FavoritesEntity::toDomain);
    }

    @Override
    @Transactional
    public void save(Favorites favorites) {
        UserEntity userEntity = userJpaRepository.findById(favorites.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + favorites.getUserId()));
        AdoptionEntity adoptionEntity = adoptionJpaRepository.findById(favorites.getAdoptionId())
                .orElseThrow(() -> new IllegalArgumentException("Adoption not found: " + favorites.getAdoptionId()));

        FavoritesEntity entity = FavoritesEntity.from(favorites, userEntity, adoptionEntity);
        favoritesJpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        favoritesJpaRepository.deleteById(id);
    }
}