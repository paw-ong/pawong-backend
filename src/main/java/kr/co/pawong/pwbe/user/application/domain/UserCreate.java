package kr.co.pawong.pwbe.user.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserCreate {
  private Long socialId;
  private String nickname;
  private String profileImage;

}
