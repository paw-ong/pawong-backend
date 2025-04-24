package kr.co.pawong.pwbe.user.presentation.controller;

import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

  private final KakaoService kakaoService;

  @PostMapping("/api/auth/kakao")
  public ResponseEntity<AuthResponse> kakaoLogin(@RequestParam("code") String code) {
    return ResponseEntity.ok(kakaoService.login(code));
  }

}
