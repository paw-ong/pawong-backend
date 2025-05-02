package kr.co.pawong.pwbe.favorites.application.domain;

import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class Favorites {
    private final Long id;               // 찜 ID
    private final Long userId;           // 사용자 ID
    private final Long targetId;         // 찜 대상 ID
    private final FavoriteTargetType targetType; // 찜 대상 타입 (유기동물, 실종동물, 봉사공고 등)

    @Builder
    public Favorites(Long id, Long userId, Long targetId, FavoriteTargetType targetType) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;
    }
}