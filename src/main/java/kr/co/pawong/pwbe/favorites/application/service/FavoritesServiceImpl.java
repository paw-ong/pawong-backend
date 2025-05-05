package kr.co.pawong.pwbe.favorites.application.service;

import kr.co.pawong.pwbe.adoption.application.service.port.AdoptionQueryRepository;
import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.application.service.port.FavoritesRepository;
import kr.co.pawong.pwbe.user.application.service.port.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final UserQueryRepository userQueryRepository;
    private final AdoptionQueryRepository adoptionQueryRepository;

    // toggle 방식의 찜 (이미 찜을 한 경우 찜 취소. 찜을 안한 경우 찜.)
    @Override
    @Transactional
    public boolean toggleFavorite(FavoritesRequest request) {
        final Long userId = request.getUserId();
        final Long adoptionId = request.getAdoptionId();

        // 1) User 존재 검증 (없으면 IllegalArgumentException)
        userQueryRepository.findByUserId(userId);

        // 2) Adoption 존재 검증 (없으면 EntityNotFoundException 등)
        adoptionQueryRepository.findByIdOrThrow(adoptionId);

        // 3) 토글 로직
        return favoritesRepository
                .findByUserIdAndAdoptionId(userId, adoptionId)
                .map(fav -> {
                    // 이미 찜되어 있으면 삭제(취소)
                    favoritesRepository.deleteById(fav.getFavoritesId());
                    log.info("찜 취소. (userId={}, adoptionId={})",
                            userId, adoptionId);
                    return false;
                })
                .orElseGet(() -> {
                    // 아직 찜하지 않았으면 새로 저장
                    Favorites newFav = Favorites.of(userId, adoptionId);
                    favoritesRepository.save(newFav);
                    log.info("찜 추가. (userId={} adoptionId={})", userId, adoptionId);
                    return true;
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Favorites> findAllByUserId(Long userId) {
        userQueryRepository.findByUserId(userId);   // 유저가 존재하는지 먼저 검증
        return favoritesRepository.findAllByUserId(userId);
    }

    /**
     *
     * @param userId
     * @param adoptionId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean checkFavoriteStatus(FavoritesRequest request) {
        Long userId = request.getUserId();
        Long adoptionId = request.getAdoptionId();

        // 유저와 공고 존재 검증
        userQueryRepository.findByUserId(userId);
        adoptionQueryRepository.findByIdOrThrow(adoptionId);

        // 사용자가 해당 공고를 찜했는지 확인 (존재하면 true, 없으면 false)
        return favoritesRepository
                .findByUserIdAndAdoptionId(userId, adoptionId)
                .isPresent();
    }
}