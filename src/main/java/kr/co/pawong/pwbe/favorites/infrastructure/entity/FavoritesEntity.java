package kr.co.pawong.pwbe.favorites.infrastructure.entity;

import jakarta.persistence.*;
import kr.co.pawong.pwbe.favorites.application.domain.Favorites;
import kr.co.pawong.pwbe.favorites.enums.FavoriteTargetType;
import kr.co.pawong.pwbe.user.infrastructure.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_id", "target_type"})
})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoritesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private Long targetId; // 찜 대상의 ID

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private FavoriteTargetType targetType; // 유기동물/실종제보/봉사공고 등 추후 확장성 고려, 현재는 ADOPTION만 존재.

    public static FavoritesEntity of(UserEntity user, Long targetId, FavoriteTargetType type) {
        return FavoritesEntity.builder()
                .user(user)
                .targetId(targetId)
                .targetType(type)
                .build();
    }

    public Favorites toDomain() {
        return Favorites.builder()
                .id(this.id)
                .userId(this.user.getUserId())
                .targetId(this.targetId)
                .targetType(this.targetType)
                .build();
    }

}
