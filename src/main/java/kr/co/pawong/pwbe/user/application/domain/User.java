package kr.co.pawong.pwbe.user.application.domain;

import java.time.LocalDate;
import kr.co.pawong.pwbe.user.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
  private Long userId;
  private Long socialId;
  private String nickname;
  private String profileImage;
  private String region;
  private String tel;
  private UserStatus status;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private LocalDate deletedAt;

  public static User from(UserCreate userCreate) {
    return User.builder()
        .socialId(userCreate.getSocialId())
        .nickname(userCreate.getNickname())
        .profileImage(userCreate.getProfileImage())
        .status(UserStatus.PENDING)
        .createdAt(LocalDate.now())
        .build();
  }

  public User update(UserUpdate userUpdate) {
    this.nickname = userUpdate.getNickname();
    this.region = userUpdate.getRegion();
    this.tel = userUpdate.getTel();
    this.updatedAt = LocalDate.now();
    this.status = UserStatus.ACTIVE;
    return this;
  }



}
