package com.self.egoboard.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  MEMBER_WRONG_EMAIL_ADDRESS_OR_PASSWORD("아이디 또는 비밀번호가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
  ;

  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
