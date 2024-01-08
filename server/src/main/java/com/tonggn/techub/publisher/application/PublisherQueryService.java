package com.tonggn.techub.publisher.application;

import com.tonggn.techub.publisher.application.dto.PublisherResponse;
import com.tonggn.techub.publisher.domain.Publisher;
import com.tonggn.techub.publisher.domain.PublisherRepository;
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
@Transactional
@RequiredArgsConstructor
public class PublisherQueryService {

  public static final String CREATED_AT = "createdAt";

  private final PublisherRepository publisherRepository;

  public Page<PublisherResponse> findAllPublishersByLatest(final Integer page, final Integer size) {
    final Pageable pageable = PageRequest.of(page, size, Sort.by(CREATED_AT).descending());
    final Page<Publisher> publishers = publisherRepository.findAll(pageable);

    final List<PublisherResponse> publisherResponses = publishers.stream()
        .map(PublisherResponse::from)
        .toList();

    final long totalElements = publishers.getTotalElements();
    return new PageImpl<>(publisherResponses, pageable, totalElements);
  }

  public List<PublisherResponse> findAllPublishers() {
    return publisherRepository.findAll().stream()
        .map(PublisherResponse::from)
        .toList();
  }
}
