package com.tonggn.techub.feed;

import com.tonggn.techub.base.BaseDatetime;
import com.tonggn.techub.publisher.Publisher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

  @Column(nullable = false, unique = true)
  private String link;

  private String description;

  private String imageUrl;

  private String pubDate;
}
