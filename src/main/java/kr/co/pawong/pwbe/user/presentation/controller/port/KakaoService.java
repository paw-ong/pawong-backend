package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface KakaoService {
  User login(String code);
  User createOrGetUser(KakaoUserResponse kakaoUserInfo);
}
