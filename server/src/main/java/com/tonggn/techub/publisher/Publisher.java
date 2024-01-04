package com.tonggn.techub.publisher;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.tonggn.techub.base.BaseDatetime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.net.URLDecoder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Publisher extends BaseDatetime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false, unique = true)
  private String rssLink;

  private String logoUrl;

  public Publisher(final String name, final String rssLink) {
    this.name = name;
    this.rssLink = URLDecoder.decode(rssLink, UTF_8);
  }

  public void updateLogoUrl(final String logoUrl) {
    this.logoUrl = logoUrl;
  }
}
