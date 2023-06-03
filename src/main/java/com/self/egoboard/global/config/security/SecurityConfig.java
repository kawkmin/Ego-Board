package com.self.egoboard.global.config.security;

import com.self.egoboard.global.config.security.filter.JwtAuthenticationFilter;
import com.self.egoboard.global.config.security.handler.JwtAccessDeniedHandler;
import com.self.egoboard.global.config.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 설정 클래스
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public BCryptPasswordEncoder encoder() {
    // 비밀번호를 DB에 저장하기 전 사용할 암호화
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .httpBasic().disable()
        .csrf().disable()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션,쿠키 사용 X

        // 예외 처리
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401 에러
        .accessDeniedHandler(jwtAccessDeniedHandler) // 403 에러

        .and()
        .authorizeRequests() // '인증'이 필요하다
        .requestMatchers("/api/mypage/**").authenticated() // 마이페이지 인증완료 후 접근 허용
        .anyRequest().permitAll(); // 나머지 허용

//        .and()
//        .headers()
//        .frameOptions().sameOrigin();

    return http.build();
  }

//  @Bean
//  public AuthenticationManager authenticationManager(
//      AuthenticationConfiguration authenticationConfiguration) throws Exception {
//    return authenticationConfiguration.getAuthenticationManager();
//  }

}