package com.tonggn.techub.crawler;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

class AtomParser {

  private AtomParser() {
  }

  public static List<ParsedFeed> parse(final Document xml) {
    return xml.select("feed > entry").stream()
        .map(AtomParser::mapToResponse)
        .toList();
  }

  private static ParsedFeed mapToResponse(final Element item) {
    final String title = ParserUtils.selectFirstTextOrEmpty(item, "title");
    final String link = ParserUtils.selectFirstAttrOrEmpty(item, "link", "href");
    final String description = ParserUtils.selectFirstTextOrEmpty(item, "summary");
    final String pubDate = ParserUtils.selectFirstTextOrEmpty(item, "updated");
    final String image = ParserUtils.selectFirstTextOrEmpty(item, "image");
    return new ParsedFeed(title, link, description, pubDate, image);
  }
}
