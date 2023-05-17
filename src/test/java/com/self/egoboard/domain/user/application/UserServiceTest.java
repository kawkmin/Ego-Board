package com.self.egoboard.domain.user.application;

import static org.junit.jupiter.api.Assertions.*;

import com.self.egoboard.IntegrationTest;
import com.self.egoboard.domain.user.entity.User;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest extends IntegrationTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = userTestHelper.generate();
  }

  @Test
  @DisplayName("유저가 성공적으로 생성된다")
  void should_success_when_createUser() {
    em.flush();
    em.clear();

    Optional<User> findUser = userRepository.findById(user.getId());

    Assertions.assertThat(findUser.isEmpty()).isFalse();
  }
}