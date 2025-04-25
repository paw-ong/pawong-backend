package kr.co.pawong.pwbe.user.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdate {
  private String nickname;
  private String region;
  private String tel;

  public User toDomain(Long userId) {
    return User.builder()
        .userId(userId)
        .nickname(nickname)
        .region(region)
        .tel(tel)
        .build();
  }

}
