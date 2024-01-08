package com.tonggn.techub.feed.application;

import com.tonggn.techub.feed.application.dto.FeedResponse;
import com.tonggn.techub.feed.domain.Feed;
import com.tonggn.techub.feed.domain.FeedRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedQueryService {

  public static final String CREATED_AT = "createdAt";

  private final FeedRepository feedRepository;

  public Page<FeedResponse> findAllFeedsByLatest(final Integer page, final Integer size) {
    final Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_AT).descending());
    final Page<Feed> feeds = feedRepository.findAll(pageable);

    final List<FeedResponse> feedResponses = feeds.stream()
        .map(FeedResponse::from)
        .toList();

    final long totalElements = feeds.getTotalElements();
    return new PageImpl<>(feedResponses, pageable, totalElements);
  }
}
