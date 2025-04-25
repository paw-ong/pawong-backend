package kr.co.pawong.pwbe.user.infrastructure.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private String jwtToken = "abcde";
    public String getJwtToken(Long UserId) {
        return jwtToken;
    }
}