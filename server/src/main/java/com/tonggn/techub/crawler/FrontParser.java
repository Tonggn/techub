package com.tonggn.techub.crawler;

import static org.jsoup.parser.Parser.xmlParser;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class FrontParser {

  private final Map<RssType, Function<Document, List<ParsedFeed>>> parserMap = new EnumMap<>(
      RssType.class);

  public FrontParser() {
    parserMap.put(RssType.RSS, RssParser::parse);
    parserMap.put(RssType.RDF, RdfParser::parse);
    parserMap.put(RssType.ATOM, AtomParser::parse);
  }

  public List<ParsedFeed> parse(final Document document) {
    final Document xml = document.parser(xmlParser());
    final RssType type = getRssType(xml);
    return parserMap.get(type).apply(xml);
  }

  private RssType getRssType(final Document xml) {
    final String rootTagName = xml.child(0).tagName();
    return RssType.of(rootTagName);
  }
}
