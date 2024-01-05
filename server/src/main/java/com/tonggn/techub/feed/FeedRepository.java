package com.tonggn.techub.feed;

import com.tonggn.techub.publisher.Publisher;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

  List<Feed> findAllByPublisherAndLinkIn(Publisher publisher, List<String> feedLinks);
}
