package kr.co.pawong.pwbe.user.application.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserUpdate {
  private String nickname;
  private String region;
  private String tel;

}
