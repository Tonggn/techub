package com.tonggn.techub.feed;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.tonggn.techub.base.BaseDatetime;
import com.tonggn.techub.publisher.Publisher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.net.URLDecoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseDatetime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Publisher publisher;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, unique = true, length = 500)
  private String link;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(length = 500)
  private String imageUrl;

  @Column(length = 50)
  private String pubDate;

  public Feed(
      final Publisher publisher,
      final String title,
      final String link,
      final String description,
      final String imageUrl,
      final String pubDate
  ) {
    this.publisher = publisher;
    this.title = title;
    this.link = URLDecoder.decode(link, UTF_8);
    this.description = description;
    this.imageUrl = URLDecoder.decode(imageUrl, UTF_8);
    this.pubDate = pubDate;
  }
}
