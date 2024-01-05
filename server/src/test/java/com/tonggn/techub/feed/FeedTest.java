package com.tonggn.techub.feed;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import fixture.FeedBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeedTest {

  @Test
  @DisplayName("퍼센트 인코딩된 url을 디코딩한다")
  void decodeRssLinkTest() {
    // given
    final String expect = "https://rss.link/퍼센트_인코딩";
    final String url = "https://rss.link/%ED%8D%BC%EC%84%BC%ED%8A%B8_%EC%9D%B8%EC%BD%94%EB%94%A9";

    // when
    final Feed feed = new FeedBuilder()
        .setLink(url)
        .setImage(url)
        .build();

    // then
    assertAll(
        () -> assertThat(feed.getLink()).isEqualTo(expect),
        () -> assertThat(feed.getImageUrl()).isEqualTo(expect)
    );
  }
}
