package com.tonggn.techub.feed.scheduling;

import com.tonggn.techub.crawler.FrontParser;
import com.tonggn.techub.crawler.ParsedFeed;
import com.tonggn.techub.crawler.RssClient;
import com.tonggn.techub.feed.application.FeedCommandService;
import com.tonggn.techub.publisher.application.PublisherQueryService;
import com.tonggn.techub.publisher.application.dto.PublisherResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulingRegister {

  private final FrontParser frontParser;
  private final FeedCommandService feedCommandService;
  private final PublisherQueryService publisherQueryService;

  // cron = "초 분 시 일 월 요일"
  @Scheduled(cron = "0 0 * * * *")
  public void scheduleFeedUpdate() {
    final List<PublisherResponse> publishers = publisherQueryService.findAllPublishers();

    for (final PublisherResponse publisher : publishers) {
      final Document response = RssClient.request(publisher.rssLink());
      final List<ParsedFeed> feedResponses = frontParser.parse(response);
      feedCommandService.saveAll(publisher.id(), feedResponses);
    }
  }
}
