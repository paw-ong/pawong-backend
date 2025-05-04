package kr.co.pawong.pwbe.user.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.enums.UserStatus;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${spring.security.base-url}")
    private String baseUrl;
    private final UserQueryService userQueryService;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2AuthenticationSuccessHandler(UserQueryService userQueryService, JwtTokenProvider jwtTokenProvider) {
      this.userQueryService = userQueryService;
      this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String socialId = oauthUser.getName();
        User user = userQueryService.getUserBySocialId(Long.valueOf(socialId));

        // ACTIVE
        String token = jwtTokenProvider.generateTokenByOauth2(authentication, user.getUserId());
        if(user.getStatus() != UserStatus.ACTIVE) {

            response.sendRedirect(baseUrl+"/signup/additional-info?token=" + token+"&status=" + user.getStatus());
            return;
        }
        // JWT를 쿼리 파라미터 등으로 클라이언트에 전달하거나 헤더에 넣어 응답함.
        response.sendRedirect(baseUrl+"/oauth2/redirect?token=" + token+"&status=ACTIVE");
    }
}
