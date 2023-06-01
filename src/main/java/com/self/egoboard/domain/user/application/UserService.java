package com.self.egoboard.domain.user.application;

import com.self.egoboard.domain.user.dao.UserRepository;
import com.self.egoboard.domain.user.dto.request.UserLoginReqDto;
import com.self.egoboard.domain.user.dto.request.UserSignUpReqDto;
import com.self.egoboard.domain.user.entity.User;
import com.self.egoboard.global.error.BusinessException;
import com.self.egoboard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void signUp(UserSignUpReqDto reqDto) {
    userRepository.save(reqDto.toEntity());
  }

  public void login(UserLoginReqDto reqDto) {
    User user = userRepository.findByEmailAddress(reqDto.emailAddress()).orElseThrow(
        () -> new BusinessException(reqDto.emailAddress(), "loginEmailAddress",
            ErrorCode.MEMBER_WRONG_EMAIL_ADDRESS_OR_PASSWORD)
    );

    if (!user.getPassword().equals(reqDto.password())) {
      throw new BusinessException(reqDto.password(), "loginPassword",
          ErrorCode.MEMBER_WRONG_EMAIL_ADDRESS_OR_PASSWORD);
    }


  }
}
