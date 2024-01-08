package com.tonggn.techub.publisher.application.dto;

import com.tonggn.techub.publisher.domain.Publisher;
import java.time.LocalDateTime;

public record PublisherResponse(
    Long id,
    String name,
    String rssLink,
    String logoUrl,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static PublisherResponse from(final Publisher publisher) {
    return new PublisherResponse(
        publisher.getId(),
        publisher.getName(),
        publisher.getRssLink(),
        publisher.getLogoUrl(),
        publisher.getCreatedAt(),
        publisher.getUpdatedAt()
    );
  }
}
