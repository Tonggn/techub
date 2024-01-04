package com.tonggn.techub.crawler;

public record ParsedFeed(
    String title,
    String link,
    String description,
    String pubDate,
    String image
) {

}
