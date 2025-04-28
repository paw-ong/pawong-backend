package kr.co.pawong.pwbe.user.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import kr.co.pawong.pwbe.user.infrastructure.security.exception.UserNotActiveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void commence(HttpServletRequest
                                 request, HttpServletResponse response, AuthenticationException
                                 authException) throws IOException, ServletException {
        log.warn("Authentication exception: {} {} {}", authException, request, response);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        String body = new ObjectMapper().writeValueAsString(
            Map.of(
                "code", authException instanceof UserNotActiveException
                    ? "NEED_ADDITIONAL_INFO"
                    : "UNAUTHORIZED",
                "message", authException.getMessage()
            )
        );
        response.getWriter().write(body);
    }

}
