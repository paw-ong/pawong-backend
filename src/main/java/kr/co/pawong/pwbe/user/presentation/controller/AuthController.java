package kr.co.pawong.pwbe.user.presentation.controller;

import kr.co.pawong.pwbe.user.infrastructure.security.CustomOAuth2User;
import kr.co.pawong.pwbe.user.presentation.controller.dto.request.SignUpRequest;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.AuthResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signUp(
      @RequestBody SignUpRequest signUpRequest,
      Authentication authentication
  ) {
    CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
    Long userId = principal.getUserId();
    return ResponseEntity.ok(authService.signUp(userId, signUpRequest.update()));
  }

}
