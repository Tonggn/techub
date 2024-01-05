package com.tonggn.techub.publisher;

import com.tonggn.techub.admin.PublisherResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PublisherService {

  private final PublisherRepository publisherRepository;

  public List<PublisherResponse> findAllPublishers() {
    return publisherRepository.findAll().stream()
        .map(PublisherResponse::from)
        .toList();
  }
}
