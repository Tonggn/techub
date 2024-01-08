package com.tonggn.techub.feed.application.dto;


import com.tonggn.techub.feed.domain.Feed;
import java.time.LocalDateTime;

public record FeedResponse(
    Long id,
    Long publisherId,
    String publisherName,
    String PublisherLogoUrl,
    String title,
    String link,
    String description,
    String imageUrl,
    LocalDateTime createdAt
) {

  public static FeedResponse from(final Feed feed) {
    return new FeedResponse(
        feed.getId(),
        feed.getPublisher().getId(),
        feed.getPublisher().getName(),
        feed.getPublisher().getLogoUrl(),
        feed.getTitle(),
        feed.getLink(),
        feed.getDescription(),
        feed.getImageUrl(),
        feed.getCreatedAt()
    );
  }
}
