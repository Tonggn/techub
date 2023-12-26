package com.tonggn.techub.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RssClient {

  private RssClient() {
  }

  public static Document request(final String url) {
    try {
      return Jsoup.connect(url)
          .maxBodySize(0)
          .get();
    } catch (final IOException e) {
      throw new RuntimeException("Failed to request: " + url);
    }
  }
}
