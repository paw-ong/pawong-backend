package kr.co.pawong.pwbe.user.infrastructure.repository.entity;

import jakarta.persistence.*;
import kr.co.pawong.pwbe.user.application.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")   // User가 예약어라 Users로 만들었습니다.
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;                  // 사용자 ID

    public User toDomain() {
        return User.builder()
                .userId(userId)
                .build();
    }

    public static UserEntity of(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .build();
    }
}
