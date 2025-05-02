package kr.co.pawong.pwbe.favorites.presentation;

import kr.co.pawong.pwbe.favorites.application.service.FavoritesService;
import kr.co.pawong.pwbe.favorites.application.service.dto.FavoritesRequest;
import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import kr.co.pawong.pwbe.favorites.presentation.dto.response.FavoritesResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FavoritesController {
    private final FavoritesService favoritesService;

    /**
     * TODO: Spring Security 적용 후 @AuthenticationPrincipal 등으로 userId를 받도록 변경
     * 현재는 요청에 바로 userId 받아옴
     */
    @PostMapping("/adoptions/{adoptionId}/favorites")
    public ResponseEntity<FavoritesResponse> likeAdoption(@PathVariable Long adoptionId,
                                                          @RequestBody Long userId) {
        FavoritesRequest request = FavoritesRequest.builder()
                .userId(userId)
                .targetId(adoptionId)
                .targetType(FavoriteTargetType.ADOPTION)
                .build();

        FavoritesResponse response = favoritesService.like(request);
        return ResponseEntity.ok(response);
    }
}
