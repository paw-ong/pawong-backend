package kr.co.pawong.pwbe.user.presentation.controller;

import jakarta.annotation.security.PermitAll;
import kr.co.pawong.pwbe.user.infrastructure.security.CustomOAuth2User;
import kr.co.pawong.pwbe.user.presentation.controller.dto.response.UserResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

  private final UserQueryService userQueryService;

  @PermitAll
  @GetMapping("/me")
  public ResponseEntity<UserResponse> getUser(
      Authentication authentication
  ) {
    CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
    Long userId = principal.getUserId();
    UserResponse userResponse = new UserResponse(userQueryService.getUser(userId));

    return ResponseEntity.ok(userResponse);
  }

}
