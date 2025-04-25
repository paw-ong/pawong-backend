package kr.co.pawong.pwbe.user.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private Key secretKey;
    private static final long tokenValidityInMs = 1000 * 60 * 60 * 24; // 1 day

    public JwtTokenProvider(@Value("${auth.jwt.secret-key}") String key) {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(Long userId) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("user_id", userId)
            .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMs))
            .signWith(secretKey)
            .compact();
    }
}