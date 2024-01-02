package com.tonggn.techub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${techub.images.dir}")
  private String imagesDir;
  @Value("${techub.images.url}")
  private String imagesUrl;

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler(imagesUrl + "/**")
        .addResourceLocations("file:" + imagesDir + "/");
  }
}
