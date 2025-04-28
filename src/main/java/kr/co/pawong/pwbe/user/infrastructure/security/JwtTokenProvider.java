package kr.co.pawong.pwbe.user.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final Key secretKey;
    @Getter
    private static final long tokenValidityInMs = 60L * 60 * 1000 * 1000;

    public JwtTokenProvider(@Value("${spring.security.jwt.secret-key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateTokenByOauth2(Authentication authentication, Long userId) {
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        return getToken(
            userId,
            oauth2User.getName(),
            oauth2User.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
        );
    }

    public String generateToken(Authentication authentication, Long userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return getToken(userId, userDetails.getUsername(), roles);
    }

    private String getToken(Long userId, String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMs);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))  // 사용자 식별 정보 (주로 username)
                .claim("roles", roles)
                .claim("username", username)          // 필요시 추가 정보// 권한 정보
                .setIssuedAt(now)                       // 발행 시간
                .setExpiration(validity)                // 만료 시간
                .signWith(this.secretKey)// 서명 알고리즘
                .compact();
    }

    public UserDetails getUserDetails(String token, Long socialId) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.valueOf(claims.getSubject());
        List<String> roles = claims.get("roles", List.class);

        return new CustomUserDetails(
            userId,
            socialId,
            roles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }

    public boolean validateToken(String token) {
        if (token != null && getUsername(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    public String generateJwtToken(Long userId) {
        return "Bearer test";
    }

}
