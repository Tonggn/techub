package com.tonggn.techub.admin;

import com.tonggn.techub.publisher.PublisherAddRequest;
import com.tonggn.techub.publisher.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final PublisherService publisherService;

  @GetMapping("/publishers")
  public String showPublishers(
      final Model model,
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "20") final Integer size
  ) {
    model.addAttribute("publishers", publisherService.findPageByLatest(page, size));
    return "admin/publishers";
  }

  @PostMapping("/publishers")
  public String addPublisher(@Valid final PublisherAddRequest request) {
    publisherService.addPublisher(request);
    return "redirect:/admin/publishers";
  }
}
