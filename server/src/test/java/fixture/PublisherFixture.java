package fixture;

import com.tonggn.techub.publisher.Publisher;
import java.util.UUID;

public class PublisherFixture {

  public static Publisher newPublisher() {
    final String name = UUID.randomUUID().toString();
    final String rssLink = "https://rss.link/" + UUID.randomUUID();
    return new Publisher(name, rssLink);
  }
}
