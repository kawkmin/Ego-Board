package com.self.egoboard.domain.user.application;

import com.self.egoboard.domain.user.dao.UserRepository;
import com.self.egoboard.domain.user.dto.request.UserSignUpReqDto;
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
}
