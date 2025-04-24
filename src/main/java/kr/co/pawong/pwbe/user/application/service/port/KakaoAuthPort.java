package kr.co.pawong.pwbe.user.application.service.port;

import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface KakaoAuthPort {
  String getKakaoAccessToken(String code);
  KakaoUserResponse getKakaoUserInfo(String code);

}
