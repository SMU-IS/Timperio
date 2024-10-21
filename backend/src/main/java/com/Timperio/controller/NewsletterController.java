package com.Timperio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Timperio.service.impl.NewsletterService;
import com.Timperio.dto.NewsletterRequestDTO;
import com.Timperio.dto.NewsletterResponseDTO;

@RestController
@RequestMapping("api/v1/newsletter")
public class NewsletterController {

    @Autowired
    private NewsletterService newsletterService;
    
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return this.newsletterService.healthCheck();
    }
    
    @PostMapping("/send")
    public ResponseEntity<String> sendNewsletter(@RequestBody NewsletterRequestDTO newsletterRequestDTO) {
        return this.newsletterService.sendNewsletter(newsletterRequestDTO);
    }
}
