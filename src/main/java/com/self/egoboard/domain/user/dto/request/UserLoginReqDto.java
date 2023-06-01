package com.self.egoboard.domain.user.dto.request;

public record UserLoginReqDto(
    String emailAddress,
    String password
) {

}
