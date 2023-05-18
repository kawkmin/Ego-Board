package com.self.egoboard.domain.user.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.self.egoboard.IntegrationTest;
import com.self.egoboard.domain.user.dto.request.UserSignUpReqDto;
import com.self.egoboard.domain.user.entity.User;
import com.self.egoboard.domain.user.entity.embedded.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

class UserControllerTest extends IntegrationTest {

  @Nested
  @DisplayName("유저 생성")
  class createUser {

    private User user;

    @BeforeEach
    void setUp() {
      user = userTestHelper.generate();
    }

    private ResultActions mockMvcTest(UserSignUpReqDto userSignUpReqDto, ResultMatcher Created)
        throws Exception {
      return mockMvc.perform(post("/api/v1/user/sign-up")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(userSignUpReqDto)))
          .andExpect(Created);
    }

    @Test
    @DisplayName("유저 생성에 성공한다.")
    void should_201CREATE_when_createUser() throws Exception {

      UserSignUpReqDto userSignUpReqDto = new UserSignUpReqDto(
          user.getEmailAddress(),
          user.getPassword(),
          user.getUsername(),
          user.getNickname(),
          user.getAddress()
      );

      mockMvcTest(userSignUpReqDto, status().isCreated())
          .andDo(document("user-create",
              requestFields(
                  fieldWithPath("emailAddress").description("이메일 주소"),
                  fieldWithPath("password").description("비밀번호"),
                  fieldWithPath("username").description("유저 이름"),
                  fieldWithPath("nickname").description("닉네임"),
                  fieldWithPath("address.city").description("주소 - 도시"),
                  fieldWithPath("address.street").description("주소 - 거리"),
                  fieldWithPath("address.zipcode").description("주소 - 우편번호")
              )));
    }


    @ParameterizedTest
    @ValueSource(strings = {"Hello", "JUnit@", "@Parameterized", "1234"})
    @DisplayName("이메일 형식이 다르면, 유저 생성에 실패한다.")
    void should_400BadRequest_when_diffEmail(String param) throws Exception {
      UserSignUpReqDto userSignUpReqDto = new UserSignUpReqDto(
          param,
          user.getPassword(),
          user.getUsername(),
          user.getNickname(),
          user.getAddress()
      );

      mockMvcTest(userSignUpReqDto, status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "JUnit@", "@Parameterized", "1234", "abcdefghizk"})
    @DisplayName("페스워드 형식이 다르면, 유저 생성에 실패한다.")
    void should_400BadRequest_when_diffPassword(String param) throws Exception {
      UserSignUpReqDto userSignUpReqDto = new UserSignUpReqDto(
          user.getEmailAddress(),
          param,
          user.getUsername(),
          user.getNickname(),
          user.getAddress()
      );

      mockMvcTest(userSignUpReqDto, status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefghizkzxcvasdf"})
    @DisplayName("페스워드 형식이 다르면, 유저 생성에 실패한다.")
    void should_400BadRequest_when_diffNickname(String param) throws Exception {
      UserSignUpReqDto userSignUpReqDto = new UserSignUpReqDto(
          user.getEmailAddress(),
          user.getPassword(),
          user.getUsername(),
          param,
          user.getAddress()
      );

      mockMvcTest(userSignUpReqDto, status().isBadRequest());
    }

    @ParameterizedTest
    @CsvSource({"as, abcdefghizkzxcvasdf, 12345", "121, street12, 123456", "city121, 123, 123456"})
    @DisplayName("페스워드 형식이 다르면, 유저 생성에 실패한다.")
    void should_400BadRequest_when_diffAddress(String city, String street, String zipcode)
        throws Exception {
      UserSignUpReqDto userSignUpReqDto = new UserSignUpReqDto(
          user.getEmailAddress(),
          user.getPassword(),
          user.getUsername(),
          user.getNickname(),
          Address.builder()
              .city(city)
              .street(street)
              .zipcode(zipcode)
              .build()
      );

      mockMvcTest(userSignUpReqDto, status().isBadRequest());
    }
  }
}