package kr.co.pawong.pwbe.user.infrastructure.security.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationExceptionTranslator translator;
    private final FilterErrorResponseSender sender;

    public CustomAuthenticationEntryPoint(
            AuthenticationExceptionTranslator translator,
            FilterErrorResponseSender sender
    ) {
        this.translator = translator;
        this.sender = sender;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        log.warn("Authentication exception: {} {} {}", authException, request, response);
        sender.send(response, translator.translate(authException));
    }

}
