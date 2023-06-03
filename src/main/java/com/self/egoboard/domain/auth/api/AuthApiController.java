package com.self.egoboard.domain.auth.api;

import com.self.egoboard.domain.auth.application.AuthService;
import com.self.egoboard.domain.auth.dto.AuthDto;
import com.self.egoboard.domain.user.application.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {

  private final AuthService authService;
  private final UserService userService;
  private final BCryptPasswordEncoder encoder;

  private final long COOKIE_EXPIRATION = 7776000; // 90일

  // 회원가입
  @PostMapping("/signup")
  public ResponseEntity<Void> signup(@RequestBody @Valid AuthDto.SignupDto signupDto) {
    String encodedPassword = encoder.encode(signupDto.getPassword());
    AuthDto.SignupDto newSignupDto = AuthDto.SignupDto.encodePassword(signupDto, encodedPassword);

    userService.registerUser(newSignupDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // 로그인 -> 토큰 발급
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid AuthDto.LoginDto loginDto) {
    // User 등록 및 Refresh Token 저장
    AuthDto.TokenDto tokenDto = authService.login(loginDto);

    // RT 저장
    HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
        .maxAge(COOKIE_EXPIRATION)
        .httpOnly(true)
        .secure(true)
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
        // AT 저장
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
        .build();
  }

  @PostMapping("/validate")
  public ResponseEntity<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
    if (!authService.validate(requestAccessToken)) {
      return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
    }
  }

  // 토큰 재발급
  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
      @RequestHeader("Authorization") String requestAccessToken) {
    AuthDto.TokenDto reissuedTokenDto = authService.reissue(requestAccessToken,
        requestRefreshToken);

    if (reissuedTokenDto != null) { // 토큰 재발급 성공
      // RT 저장
      log.info("토큰 재발급 성공");
      ResponseCookie responseCookie = ResponseCookie.from("refresh-token",
              reissuedTokenDto.getRefreshToken())
          .maxAge(COOKIE_EXPIRATION)
          .httpOnly(true)
          .secure(true)
          .build();
      return ResponseEntity
          .status(HttpStatus.OK)
          .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
          // AT 저장
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
          .build();

    } else { // Refresh Token 탈취 가능성
      // Cookie 삭제 후 재로그인 유도
      ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
          .maxAge(0)
          .path("/")
          .build();
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
          .build();
    }
  }

  // 로그아웃
  @PostMapping("/logout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
    authService.logout(requestAccessToken);
    ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
        .maxAge(0)
        .path("/")
        .build();

    return ResponseEntity
        .status(HttpStatus.OK)
        .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
        .build();
  }
}