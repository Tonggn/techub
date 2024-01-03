package com.tonggn.techub.publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PublisherAddRequest {

  @NotBlank
  @Size(min = 1, max = 255)
  private String name;

  @URL(protocol = "https")
  @NotBlank
  @Size(min = 1, max = 255)
  private String rssLink;

  private MultipartFile logoFile;

  public boolean hasLogoFile() {
    return logoFile != null && !logoFile.isEmpty();
  }
}
