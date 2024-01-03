package com.tonggn.techub.publisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SpringBootTest
class PublisherAddRequestTest {

  @Autowired
  private Validator validator;

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {" ", "  "})
  @DisplayName("name 필드가 비어있으면 유효성 검사에 실패한다")
  void nameBlankTest(final String name) {
    // given
    final PublisherAddRequest request = new PublisherAddRequest();
    request.setName(name);
    request.setRssLink("https://valid.url");

    // when
    final Errors errors = validator.validateObject(request);

    // then
    assertAll(
        () -> assertThat(errors.getFieldError("name").getCode()).isEqualTo("NotBlank"),
        () -> assertThat(errors.hasFieldErrors("rssLink")).isFalse(),
        () -> assertThat(errors.hasFieldErrors("logoFile")).isFalse()
    );
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 256})
  @DisplayName("name 필드가 1자 미만, 255자 초과일 경우 유효성 검사에 실패한다")
  void nameLengthUnderOrOverTest(final int repeatCount) {
    //given
    final String name = "a".repeat(repeatCount);

    final PublisherAddRequest request = new PublisherAddRequest();
    request.setName(name);
    request.setRssLink("https://valid.url");

    // when
    final Errors errors = validator.validateObject(request);

    // then
    assertAll(
        () -> assertThat(errors.getFieldError("name").getCode()).containsAnyOf("Size", "NotBlank"),
        () -> assertThat(errors.hasFieldErrors("rssLink")).isFalse(),
        () -> assertThat(errors.hasFieldErrors("logoFile")).isFalse()
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"https", "http://", "ftp://", "ssh://", "file://"})
  @DisplayName("rssLink의 프로토콜이 https가 아닐 경우 유효성 검사에 실패한다")
  void rssLinkInvalidProtocolTest(final String protocol) {
    // given
    final PublisherAddRequest request = new PublisherAddRequest();
    request.setName("valid name");
    request.setRssLink(protocol + "rss.link");

    // when
    final Errors errors = validator.validateObject(request);

    // then
    assertAll(
        () -> assertThat(errors.hasFieldErrors("name")).isFalse(),
        () -> assertThat(errors.getFieldError("rssLink").getCode()).isEqualTo("URL"),
        () -> assertThat(errors.hasFieldErrors("logoFile")).isFalse()
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"https:", "https:/", "https://"})
  @DisplayName("rssLink의 프로토콜은 https여야 한다")
  void rssLinkValidProtocolTest(final String protocol) {
    // given
    final PublisherAddRequest request = new PublisherAddRequest();
    request.setName("valid name");
    request.setRssLink(protocol + "rss.link");

    // when
    final Errors errors = validator.validateObject(request);

    // then
    assertAll(
        () -> assertThat(errors.hasFieldErrors("name")).isFalse(),
        () -> assertThat(errors.hasFieldErrors("rssLink")).isFalse(),
        () -> assertThat(errors.hasFieldErrors("logoFile")).isFalse()
    );
  }
}
