package com.tonggn.techub.publisher.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.tonggn.techub.publisher.application.dto.PublisherResponse;
import com.tonggn.techub.publisher.domain.Publisher;
import com.tonggn.techub.publisher.domain.PublisherRepository;
import fixture.PublisherBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class PublisherQueryServiceTest {

  @Autowired
  private PublisherRepository publisherRepository;
  @Autowired
  private PublisherQueryService publisherQueryService;

  @AfterEach
  void tearDown() {
    publisherRepository.deleteAll();
  }

  @Test
  @DisplayName("최신순으로 Publisher 목록을 페이징한다")
  void paginationPublisherByLatest() {
    // given
    final Publisher publisher1 = new PublisherBuilder().build();
    final Publisher publisher2 = new PublisherBuilder().build();
    final Publisher publisher3 = new PublisherBuilder().build();

    publisherRepository.save(publisher1);
    publisherRepository.save(publisher2);
    publisherRepository.save(publisher3);

    // when
    final int pageNum = 0;
    final int size = 2;
    final Page<PublisherResponse> page = publisherQueryService
        .findAllPublishersByLatest(pageNum, size);

    // then
    assertAll(
        () -> assertThat(page.getContent())
            .hasSize(size),
        () -> assertThat(page.getContent())
            .extracting("name")
            .containsExactly(publisher3.getName(), publisher2.getName()),
        () -> assertThat(page.getContent())
            .extracting("rssLink")
            .containsExactly(publisher3.getRssLink(), publisher2.getRssLink())
    );
  }
}
