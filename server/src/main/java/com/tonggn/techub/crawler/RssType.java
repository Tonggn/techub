package com.tonggn.techub.crawler;

public enum RssType {
  RSS("rss"), ATOM("feed"), RDF("rdf:RDF");

  private final String rootTagName;

  RssType(final String rootTagName) {
    this.rootTagName = rootTagName;
  }

  public static RssType of(final String rootTagName) {
    for (final RssType type : values()) {
      if (type.rootTagName.equals(rootTagName)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown root tag: " + rootTagName);
  }
}
