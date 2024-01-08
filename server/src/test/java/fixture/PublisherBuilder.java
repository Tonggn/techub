package fixture;

import com.tonggn.techub.publisher.domain.Publisher;
import java.util.UUID;

public class PublisherBuilder {

  private String name = UUID.randomUUID().toString();
  private String rssLink = "https://rss.link" + UUID.randomUUID();

  public PublisherBuilder setName(final String name) {
    this.name = name;
    return this;
  }

  public PublisherBuilder setRssLink(final String rssLink) {
    this.rssLink = rssLink;
    return this;
  }

  public Publisher build() {
    return new Publisher(name, rssLink);
  }
}
