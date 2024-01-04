package com.tonggn.techub.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.tonggn.techub.publisher.Publisher;
import com.tonggn.techub.publisher.PublisherAddRequest;
import com.tonggn.techub.publisher.PublisherRepository;
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
    final String name1 = "publisher1";
    final String name2 = "publisher2";
    final String name3 = "publisher3";

    final String link1 = "https://rss1.link";
    final String link2 = "https://rss2.link";
    final String link3 = "https://rss3.link";

    final Publisher publisher1 = new Publisher(name1, link1);
    final Publisher publisher2 = new Publisher(name2, link2);
    final Publisher publisher3 = new Publisher(name3, link3);

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
            .containsExactly(name3, name2),
        () -> assertThat(page.getContent())
            .extracting("rssLink")
            .containsExactly(link3, link2)
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
      final String rssLink1 = "https://rss.link/1";
      final String rssLink2 = "https://rss.link/2";

      final Publisher publisher = new Publisher(request.getName(), rssLink1);
      publisherRepository.save(publisher);

      request.setRssLink(rssLink2);

      // when
      // then
      assertThatThrownBy(() -> adminService.addPublisher(request))
          .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Publisher의 rssLink가 중복될 경우 예외가 발생한다")
    void addPublisherWithDuplicatedRssLink() {
      // given
      final String name1 = "publisher1";
      final String name2 = "publisher2";

      final Publisher publisher = new Publisher(name1, request.getRssLink());
      publisherRepository.save(publisher);

      request.setName(name2);

      // when
      // then
      assertThatThrownBy(() -> adminService.addPublisher(request))
          .isInstanceOf(DataIntegrityViolationException.class);
    }
  }
}
