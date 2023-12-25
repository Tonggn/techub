package com.tonggn.techub.crawler;

public record ParseResponse(
    String title,
    String link,
    String description,
    String pubDate,
    String image
) {

}
