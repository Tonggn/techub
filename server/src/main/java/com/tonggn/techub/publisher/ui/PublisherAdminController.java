package com.tonggn.techub.publisher.ui;

import com.tonggn.techub.publisher.application.PublisherCommandService;
import com.tonggn.techub.publisher.application.PublisherQueryService;
import com.tonggn.techub.publisher.application.dto.PublisherAddRequest;
import com.tonggn.techub.publisher.application.dto.PublisherResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/publishers")
@RequiredArgsConstructor
public class PublisherAdminController {

  private final PublisherQueryService publisherQueryService;
  private final PublisherCommandService publisherCommandService;

  @GetMapping
  public String showPublishers(
      final Model model,
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "20") final Integer size
  ) {
    final Page<PublisherResponse> publishers = publisherQueryService
        .findAllPublishersByLatest(page, size);

    model.addAttribute("publishers", publishers);

    return "admin/publishers";
  }

  @PostMapping
  public String addPublisher(@Valid final PublisherAddRequest request) {
    publisherCommandService.addPublisher(request);
    return "redirect:/admin/publishers";
  }
}
