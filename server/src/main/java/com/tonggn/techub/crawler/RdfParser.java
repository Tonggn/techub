package com.tonggn.techub.crawler;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class RdfParser {

  private RdfParser() {
  }

  public static List<ParsedFeed> parse(final Document xml) {
    return xml.select("channel > item").stream()
        .map(RdfParser::mapToResponse)
        .toList();
  }

  private static ParsedFeed mapToResponse(final Element item) {
    final String title = ParserUtils.selectFirstTextOrEmpty(item, "title");
    final String link = ParserUtils.selectFirstTextOrEmpty(item, "link");
    final String description = ParserUtils.selectFirstTextOrEmpty(item, "description");
    final String pubDate = ParserUtils.selectFirstTextOrEmpty(item, "dc:date");
    final String image = ParserUtils.selectFirstTextOrEmpty(item, "image");
    return new ParsedFeed(title, link, description, pubDate, image);
  }
}
