package kr.co.pawong.pwbe.user.infrastructure.external.dto;

import lombok.Data;

@Data
public class KakaoTokenResponse {
  private String access_token;
  private String token_type;
  private Long expires_in;
  private String refresh_token;
  private Long refresh_token_expires_in;
  private String scope;
}
