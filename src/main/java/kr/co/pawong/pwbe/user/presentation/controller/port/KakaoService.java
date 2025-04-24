package kr.co.pawong.pwbe.user.presentation.controller.port;

import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface KakaoService {
  AuthResponse login(String code);

}
