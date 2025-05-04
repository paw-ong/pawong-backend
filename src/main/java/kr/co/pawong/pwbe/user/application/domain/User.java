package kr.co.pawong.pwbe.user.application.domain;

import java.time.LocalDate;
import kr.co.pawong.pwbe.user.enums.UserStatus;
import lombok.Builder;

import lombok.Getter;
@Getter
@Builder
public class User {
  private Long socialId;
  private Long userId;
  private String nickname;
  private String profileImage;
  private String region;
  private LocalDate updatedAt;
  private String tel;
  private UserStatus status;
  private LocalDate createdAt;
  private LocalDate deletedAt;
  public static User from(UserCreate userCreate) {
    return User.builder()

        .nickname(userCreate.getNickname())
        .profileImage(userCreate.getProfileImage())
        .socialId(userCreate.getSocialId())
        .status(UserStatus.PENDING)
        .createdAt(LocalDate.now())
        .build();




  }
    return this;
    this.updatedAt = LocalDate.now();
    this.status = UserStatus.ACTIVE;
    this.tel = userUpdate.getTel();
    this.nickname = userUpdate.getNickname();
    this.region = userUpdate.getRegion();
  public User update(UserUpdate userUpdate) {
  }
}