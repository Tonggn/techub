package com.tonggn.techub.feed.application;

import com.tonggn.techub.crawler.ParsedFeed;
import com.tonggn.techub.feed.domain.Feed;
import com.tonggn.techub.feed.domain.FeedRepository;
import com.tonggn.techub.publisher.domain.Publisher;
import com.tonggn.techub.publisher.domain.PublisherRepository;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedCommandService {

  private final FeedRepository feedRepository;
  private final PublisherRepository publisherRepository;

  public void saveAll(final Long publisherId, final List<ParsedFeed> parsedFeeds) {
    final Publisher publisher = publisherRepository.findById(publisherId)
        .orElseThrow();
    final List<Feed> newFeeds = filterNewFeeds(publisher, parsedFeeds);
    feedRepository.saveAll(newFeeds);
  }

  private List<Feed> filterNewFeeds(final Publisher publisher, final List<ParsedFeed> parsedFeeds) {
    final List<Feed> feeds = parsedFeeds.stream()
        .map(mapToFeed(publisher))
        .toList();

    final List<String> feedLinks = feeds.stream()
        .map(Feed::getLink)
        .toList();

    final List<String> savedFeedLinks = feedRepository.findAllByPublisherAndLinkIn(publisher,
            feedLinks)
        .stream()
        .map(Feed::getLink)
        .toList();

    return feeds.stream()
        .filter(newFeed(savedFeedLinks))
        .toList();
  }

  private Predicate<Feed> newFeed(final List<String> savedLinks) {
    return feed -> !savedLinks.contains(feed.getLink());
  }

  private Function<ParsedFeed, Feed> mapToFeed(final Publisher publisher) {
    return parsedFeed -> new Feed(
        publisher,
        parsedFeed.title(),
        parsedFeed.link(),
        parsedFeed.description(),
        parsedFeed.image(),
        parsedFeed.pubDate()
    );
  }
}
