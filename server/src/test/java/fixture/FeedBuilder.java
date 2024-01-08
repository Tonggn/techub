package fixture;

import com.tonggn.techub.feed.domain.Feed;
import com.tonggn.techub.publisher.domain.Publisher;
import java.time.LocalDateTime;
import java.util.UUID;

public class FeedBuilder {

  private Publisher publisher;
  private String title = UUID.randomUUID().toString();
  private String link = "https://rss.link/" + UUID.randomUUID();
  private String description = UUID.randomUUID().toString();
  private String image = "https://image.url/" + UUID.randomUUID();
  private String pubDate = LocalDateTime.now().toString();

  public FeedBuilder setPublisher(final Publisher publisher) {
    this.publisher = publisher;
    return this;
  }

  public FeedBuilder setTitle(final String title) {
    this.title = title;
    return this;
  }

  public FeedBuilder setLink(final String link) {
    this.link = link;
    return this;
  }

  public FeedBuilder setDescription(final String description) {
    this.description = description;
    return this;
  }

  public FeedBuilder setImage(final String image) {
    this.image = image;
    return this;
  }

  public FeedBuilder setPubDate(final String pubDate) {
    this.pubDate = pubDate;
    return this;
  }

  public Feed build() {
    return new Feed(publisher, title, link, description, image, pubDate);
  }
}
