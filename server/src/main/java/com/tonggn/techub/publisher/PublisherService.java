package com.tonggn.techub.publisher;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublisherService {

  private final PublisherRepository publisherRepository;

  public Page<PublisherResponse> findPageByLatest(final Integer page, final Integer size) {
    final Pageable pageable = PageRequest.of(page, size);
    final Page<Publisher> publishers = publisherRepository.findAllByOrderByCreatedAtDesc(pageable);

    final List<PublisherResponse> publisherResponses = publishers.stream()
        .map(PublisherResponse::from)
        .toList();

    return new PageImpl<>(publisherResponses, pageable, publishers.getTotalElements());
  }
}
