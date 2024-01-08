package com.tonggn.techub.publisher.application;

import com.tonggn.techub.publisher.application.dto.PublisherAddRequest;
import com.tonggn.techub.publisher.application.dto.PublisherResponse;
import com.tonggn.techub.publisher.domain.Publisher;
import com.tonggn.techub.publisher.domain.PublisherRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class PublisherCommandService {

  private final String logoDir;
  private final String logoUrl;
  private final PublisherRepository publisherRepository;

  public PublisherCommandService(
      @Value("${techub.images.publisher.logo-dir}") final String logoDir,
      @Value("${techub.images.publisher.logo-url}") final String logoUrl,
      final PublisherRepository publisherRepository
  ) {
    this.logoDir = logoDir;
    this.logoUrl = logoUrl;
    this.publisherRepository = publisherRepository;
  }

  @Transactional
  public PublisherResponse addPublisher(final PublisherAddRequest request) {
    final Publisher publisher = new Publisher(request.getName(), request.getRssLink());
    publisherRepository.save(publisher);

    if (request.hasLogoFile()) {
      final String filename = generateFilename(request.getLogoFile());
      saveFile(request.getLogoFile(), filename);

      final String logoUrl = this.logoUrl + "/" + filename;
      publisher.updateLogoUrl(logoUrl);
    }

    return PublisherResponse.from(publisher);
  }

  private void saveFile(final MultipartFile file, final String filename) {
    final Path path = Path.of(logoDir, filename);
    try {
      final byte[] bytes = file.getBytes();
      Files.write(path, bytes);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String generateFilename(final MultipartFile file) {
    final String originalFilename = file.getOriginalFilename();
    final String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

    final String uuid = UUID.randomUUID().toString();

    return uuid + extension;
  }
}
