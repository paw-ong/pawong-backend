package kr.co.pawong.pwbe.user.presentation.controller;

import kr.co.pawong.pwbe.user.presentation.controller.dto.request.SignUpRequest;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/kakao")
  public ResponseEntity<AuthResponse> kakaoLogin(@RequestParam("code") String code) {
    return ResponseEntity.ok(authService.kakaoLogin(code));
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
    return ResponseEntity.ok(authService.signUp(signUpRequest.getUserId(), signUpRequest.update()));
  }

}
