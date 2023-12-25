package com.tonggn.techub.crawler;

import static com.tonggn.techub.crawler.ParserUtils.selectFirstTextOrEmpty;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class RssParser {

  private RssParser() {
  }

  public static List<ParseResponse> parse(final Document xml) {
    return xml.select("channel > item").stream()
        .map(RssParser::mapToResponse)
        .toList();
  }

  private static ParseResponse mapToResponse(final Element item) {
    final String title = selectFirstTextOrEmpty(item, "title");
    final String link = selectFirstTextOrEmpty(item, "link");
    final String description = selectFirstTextOrEmpty(item, "description");
    final String pubDate = selectFirstTextOrEmpty(item, "pubDate");
    final String image = selectFirstTextOrEmpty(item, "image");
    return new ParseResponse(title, link, description, pubDate, image);
  }
}
