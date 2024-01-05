package fixture;

import com.tonggn.techub.feed.Feed;
import com.tonggn.techub.publisher.Publisher;
import java.time.LocalDateTime;
import java.util.UUID;

public class FeedFixture {

  public static Feed newFeed(final Publisher publisher) {
    final String title = UUID.randomUUID().toString();
    final String link = "https://rss.link/" + UUID.randomUUID();
    final String description = UUID.randomUUID().toString();
    final String image = "https://image.url/" + UUID.randomUUID();
    final String pubDate = LocalDateTime.now().toString();

    return new Feed(
        publisher,
        title,
        link,
        description,
        image,
        pubDate
    );
  }
}
