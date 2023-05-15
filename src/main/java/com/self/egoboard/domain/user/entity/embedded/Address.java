package com.self.egoboard.domain.user.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

  private final static String CITY_EMPTY = "도시를 입력해주세요!";
  private final static String STRETT_EMPTY = "거리를 입력해주세요!";
  private final static String ZIPCODE_EMPTY = "우편번호를 입력해주세요!";
  private final static String ZIPCODE_INVALID = "우편번호는 숫자로 이뤄진, 6자리 수 입니다.";

  @NotEmpty(message = CITY_EMPTY)
  @Column(name = "city", nullable = false)
  private String city;

  @NotEmpty(message = STRETT_EMPTY)
  @Column(name = "street", nullable = false)
  private String street;

  @NotEmpty(message = ZIPCODE_EMPTY)
  @Pattern(regexp = "^[0-9]*${6}", message = ZIPCODE_INVALID)
  @Column(name = "zipcode", nullable = false)
  private String zipcode;

  @Builder
  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
