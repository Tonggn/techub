package com.tonggn.techub.config;

import com.tonggn.techub.admin.PublisherResponse;
import com.tonggn.techub.crawler.FrontParser;
import com.tonggn.techub.crawler.ParsedFeed;
import com.tonggn.techub.crawler.RssClient;
import com.tonggn.techub.feed.FeedService;
import com.tonggn.techub.publisher.PublisherService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulingConfig {

  private final FrontParser frontParser;
  private final FeedService feedService;
  private final PublisherService publisherService;

  // cron = "초 분 시 일 월 요일"
  @Scheduled(cron = "0 0 * * * *")
  public void scheduleFeedUpdate() {
    final List<PublisherResponse> publishers = publisherService.findAllPublishers();

    for (final PublisherResponse publisher : publishers) {
      final Document response = RssClient.request(publisher.rssLink());
      final List<ParsedFeed> feedResponses = frontParser.parse(response);
      feedService.saveAll(publisher.id(), feedResponses);
    }
  }
}
