package com.example.authtest.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/custom-login", "/public/**").permitAll() // 로그인 페이지 및 공용 리소스 허용
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 홈으로 이동
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 후 홈으로 이동
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 사용자가 직접 `/oauth2/authorization/keycloak`에 접근하는 경우 로그인 페이지로 이동
                            if (request.getRequestURI().startsWith("/oauth2/authorization")) {
                                response.sendRedirect("/custom-login");
                            } else {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            }
                        })
                );

        return http.build();
    }
}
