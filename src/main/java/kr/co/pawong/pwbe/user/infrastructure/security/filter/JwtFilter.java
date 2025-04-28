package kr.co.pawong.pwbe.user.infrastructure.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import kr.co.pawong.pwbe.user.application.domain.User;
import kr.co.pawong.pwbe.user.enums.UserStatus;
import kr.co.pawong.pwbe.user.infrastructure.security.JwtTokenProvider;
import kr.co.pawong.pwbe.user.infrastructure.security.exception.UserNotActiveException;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueryService userQueryService;

    public JwtFilter(
        JwtTokenProvider jwtTokenProvider,
        UserQueryService userQueryService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userQueryService = userQueryService;
    }

    private boolean isPassedUrls(String uri){
        return uri.startsWith("/login/oauth2/code"); // oauth
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        // 제외 URL 검사
        if (isPassedUrls(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);

        if (jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUsername(token);
            User user = userQueryService.getUser(Long.valueOf(userId));
            if(user == null || !user.getStatus().equals(UserStatus.ACTIVE)) {
                throw new UserNotActiveException("추가 정보가 필요합니다");
            }
            // userId와 socialId로 customOauthUserDetail을 만든 Authentication객체를 SecurityContext에 저장합니다.
            UserDetails userDetails = jwtTokenProvider.getUserDetails(token, user.getSocialId());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

