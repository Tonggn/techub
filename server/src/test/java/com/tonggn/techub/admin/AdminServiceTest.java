package com.tonggn.techub.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.tonggn.techub.publisher.Publisher;
import com.tonggn.techub.publisher.PublisherAddRequest;
import com.tonggn.techub.publisher.PublisherRepository;
import fixture.PublisherBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
class AdminServiceTest {

  @Autowired
  private AdminService adminService;
  @Autowired
  private PublisherRepository publisherRepository;

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
    final Page<PublisherResponse> page = adminService.findPublishersByLatest(pageNum, size);

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

  @Nested
  @DisplayName("Publisher를 추가한다")
  class AddPublisherTest {

    @Value("${techub.images.publisher.logo-dir}")
    String logoDir;
    @Value("${techub.images.publisher.logo-url}")
    String logoUrl;

    private PublisherAddRequest request;

    @BeforeEach
    void setUp() {
      request = new PublisherAddRequest();
      request.setName("publisher");
      request.setRssLink("https://rss.link");
    }

    @Test
    @DisplayName("로고 파일이 없을 경우 logoUrl은 null이다")
    void addPublisherWithoutLogoFile() {
      // given
      // when
      final PublisherResponse response = adminService.addPublisher(request);

      // then
      final Publisher publisher = publisherRepository.findById(response.id())
          .orElseThrow();

      assertAll(
          () -> assertThat(publisher.getName()).isEqualTo(response.name()),
          () -> assertThat(publisher.getRssLink()).isEqualTo(response.rssLink()),
          () -> assertThat(publisher.getLogoUrl()).isNull()
      );
    }

    @Test
    @DisplayName("로고 파일이 있을 경우 logoUrl은 원본 파일명을 uuid + 확장자로 변경하여 저장한 파일 경로다")
    void addPublisherWithLogoFile() {
      // given
      final String filename = "logo.png";
      final MockMultipartFile logoFile = new MockMultipartFile(filename, filename,
          MediaType.IMAGE_PNG_VALUE, "content".getBytes());
      request.setLogoFile(logoFile);

      // when
      final PublisherResponse response = adminService.addPublisher(request);

      // then
      final Publisher publisher = publisherRepository.findById(response.id())
          .orElseThrow();

      assertAll(
          () -> assertThat(publisher.getName()).isEqualTo(response.name()),
          () -> assertThat(publisher.getRssLink()).isEqualTo(response.rssLink()),
          () -> assertThat(publisher.getLogoUrl()).isEqualTo(response.logoUrl()),
          () -> assertThat(publisher.getLogoUrl()).startsWith(logoUrl),
          () -> assertThat(publisher.getLogoUrl()).doesNotEndWithIgnoringCase(filename)
      );
    }

    @Test
    @DisplayName("Publisher의 이름이 중복될 경우 예외가 발생한다")
    void addPublisherWithDuplicatedName() {
      // given
      final String duplicatedName = request.getName();

      final Publisher publisher = new PublisherBuilder()
          .setName(duplicatedName)
          .build();
      publisherRepository.save(publisher);

      request.setRssLink("https://new-rss.link");

      // when
      // then
      assertThatThrownBy(() -> adminService.addPublisher(request))
          .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Publisher의 rssLink가 중복될 경우 예외가 발생한다")
    void addPublisherWithDuplicatedRssLink() {
      // given
      final String duplicatedRssLink = request.getRssLink();

      final Publisher publisher = new PublisherBuilder()
          .setRssLink(duplicatedRssLink)
          .build();
      publisherRepository.save(publisher);

      request.setName("new name");

      // when
      // then
      assertThatThrownBy(() -> adminService.addPublisher(request))
          .isInstanceOf(DataIntegrityViolationException.class);
    }
  }
}
