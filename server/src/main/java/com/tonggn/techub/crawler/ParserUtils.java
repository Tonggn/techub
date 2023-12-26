package com.tonggn.techub.crawler;

import org.jsoup.nodes.Element;

public class ParserUtils {

  private ParserUtils() {
  }

  public static String selectFirstTextOrEmpty(final Element element, final String selector) {
    final Element item = element.selectFirst(selector);
    return item == null ? "" : item.text();
  }

  public static String selectFirstAttrOrEmpty(final Element element, final String selector,
      final String attr) {
    final Element item = element.selectFirst(selector);
    return item == null ? "" : item.attr(attr);
  }
}
