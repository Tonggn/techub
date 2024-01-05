package com.tonggn.techub.feed;

import static org.assertj.core.api.Assertions.assertThat;

import com.tonggn.techub.crawler.ParsedFeed;
import com.tonggn.techub.publisher.Publisher;
import com.tonggn.techub.publisher.PublisherRepository;
import fixture.FeedBuilder;
import fixture.PublisherBuilder;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedServiceTest {

  @Autowired
  private FeedService feedService;
  @Autowired
  private FeedRepository feedRepository;
  @Autowired
  private PublisherRepository publisherRepository;

  @AfterEach
  void tearDown() {
    feedRepository.deleteAll();
    publisherRepository.deleteAll();
  }

  @Test
  @DisplayName("새로운 피드만 저장한다")
  void saveAllTest() {
    // given
    final Publisher publisher = new PublisherBuilder().build();
    publisherRepository.save(publisher);

    final Feed newFeed = new FeedBuilder()
        .setPublisher(publisher)
        .build();
    final Feed savedFeed = new FeedBuilder()
        .setPublisher(publisher)
        .build();
    feedRepository.save(savedFeed);

    final int newFeedCount = 1;
    final long expect = feedRepository.count() + newFeedCount;

    final ParsedFeed newFeedRequest = mapToParsedFeed(newFeed);
    final ParsedFeed savedFeedRequest = mapToParsedFeed(savedFeed);

    // when
    final List<ParsedFeed> feedRequests = List.of(newFeedRequest, savedFeedRequest);
    feedService.saveAll(publisher.getId(), feedRequests);

    // then
    assertThat(feedRepository.count()).isEqualTo(expect);
  }

  private ParsedFeed mapToParsedFeed(final Feed newFeed) {
    return new ParsedFeed(
        newFeed.getTitle(),
        newFeed.getLink(),
        newFeed.getDescription(),
        newFeed.getPubDate(),
        newFeed.getImageUrl()
    );
  }
}
