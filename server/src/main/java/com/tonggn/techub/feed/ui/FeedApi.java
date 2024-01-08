package com.tonggn.techub.feed.ui;

import com.tonggn.techub.feed.application.FeedQueryService;
import com.tonggn.techub.feed.application.dto.FeedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
public class FeedApi {

  private final FeedQueryService feedQueryService;

  @GetMapping
  public Page<FeedResponse> findFeedsByLatest(
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "10") final Integer size
  ) {
    return feedQueryService.findAllFeedsByLatest(page, size);
  }
}
