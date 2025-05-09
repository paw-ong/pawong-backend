package kr.co.pawong.pwbe.user.config;

import kr.co.pawong.pwbe.user.infrastructure.security.error.CustomAuthenticationEntryPoint;
import kr.co.pawong.pwbe.user.infrastructure.security.CustomOAuth2UserService;
import kr.co.pawong.pwbe.user.infrastructure.security.JwtTokenProvider;
import kr.co.pawong.pwbe.user.infrastructure.security.OAuth2AuthenticationSuccessHandler;
import kr.co.pawong.pwbe.user.infrastructure.security.filter.JwtFilter;
import kr.co.pawong.pwbe.user.presentation.controller.port.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserQueryService userQueryService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(
            JwtFilter jwtFilter,
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService,
            CustomOAuth2UserService customOAuth2UserService,
            UserQueryService userQueryService,
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint
    ) {
        this.jwtFilter = jwtFilter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.customOAuth2UserService = customOAuth2UserService;
        this.userQueryService = userQueryService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // ExceptionTranslationFilter -> jwtFilter 실행
                .addFilterAfter(jwtFilter, ExceptionTranslationFilter.class)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/oauth2/authorization/**",     // OAuth2 로그인 요청
                                "/login/oauth2/**",             // OAuth2 code Redirect URI
                                "/oauth/authorize"              // OAuth2 Authorization Endpoint
                        ).permitAll()                         // 위 경로는 인증 없이 접근 가능
                        .requestMatchers(
                                "/api/adoptions/**",
                                "/api/adoption/**",
                                "/api/shelters/**",
                                "/api/lost-animals/**"
                        ).permitAll()
                        .anyRequest().authenticated())

                // oauth2 요청만 처리
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler())
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(userQueryService, jwtTokenProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

