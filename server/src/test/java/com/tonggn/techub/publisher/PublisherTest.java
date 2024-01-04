package com.tonggn.techub.publisher;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PublisherTest {

  @Test
  @DisplayName("퍼센트 인코딩된 rssLink를 디코딩한다.")
  void decodeRssLinkTest() {
    final String expect = "https://rss.link/퍼센트_인코딩";

    final String url = "https://rss.link/%ED%8D%BC%EC%84%BC%ED%8A%B8_%EC%9D%B8%EC%BD%94%EB%94%A9";
    final Publisher publisher = new Publisher("name", url);
    final String actual = publisher.getRssLink();

    assertThat(actual).isEqualTo(expect);
  }
}
