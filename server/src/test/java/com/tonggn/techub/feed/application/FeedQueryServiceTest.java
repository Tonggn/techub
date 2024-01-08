package com.tonggn.techub.feed.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.tonggn.techub.feed.application.dto.FeedResponse;
import com.tonggn.techub.feed.domain.Feed;
import com.tonggn.techub.feed.domain.FeedRepository;
import com.tonggn.techub.publisher.domain.PublisherRepository;
import fixture.FeedBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class FeedQueryServiceTest {

  @Autowired
  private FeedRepository feedRepository;
  @Autowired
  private PublisherRepository publisherRepository;
  @Autowired
  private FeedQueryService feedQueryService;

  @AfterEach
  void tearDown() {
    feedRepository.deleteAll();
  }

  @Test
  @DisplayName("최신순으로 Feed 목록을 페이징한다")
  void paginationPublisherByLatest() {
    // given
    final Feed feed1 = new FeedBuilder().build();
    final Feed feed2 = new FeedBuilder().build();
    final Feed feed3 = new FeedBuilder().build();

    publisherRepository.save(feed1.getPublisher());
    publisherRepository.save(feed2.getPublisher());
    publisherRepository.save(feed3.getPublisher());

    feedRepository.save(feed1);
    feedRepository.save(feed2);
    feedRepository.save(feed3);

    // when
    final int pageNum = 0;
    final int size = 2;
    final Page<FeedResponse> page = feedQueryService.findAllFeedsByLatest(pageNum, size);

    // then
    assertAll(
        () -> assertThat(page.getContent())
            .hasSize(size),
        () -> assertThat(page.getContent())
            .extracting("title")
            .containsExactly(feed3.getTitle(), feed2.getTitle()),
        () -> assertThat(page.getContent())
            .extracting("link")
            .containsExactly(feed3.getLink(), feed2.getLink()),
        () -> assertThat(page.getContent())
            .extracting("publisherName")
            .containsExactly(feed3.getPublisher().getName(), feed2.getPublisher().getName())
    );
  }
}
