package kr.co.pawong.pwbe.user.infrastructure.security;

import java.util.List;
import kr.co.pawong.pwbe.user.infrastructure.external.dto.KakaoUserResponse;
import kr.co.pawong.pwbe.user.presentation.controller.port.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final KakaoService kakaoService;

    public CustomOAuth2UserService(KakaoService kakaoService) {
      this.kakaoService = kakaoService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();

        // 회원정보 저장/업데이트
        KakaoUserResponse kakaoUserResponse = KakaoUserResponse.from(oAuth2User);
        kakaoService.createOrGetUser(kakaoUserResponse);

        SecurityContextHolder.getContext().setAuthentication(
            new OAuth2AuthenticationToken(oAuth2User, oAuth2User.getAuthorities(), provider));

        return new DefaultOAuth2User(
            List.of(new SimpleGrantedAuthority("ROLE_USER")),
            oAuth2User.getAttributes(),
            "id"
        );
    }
}
