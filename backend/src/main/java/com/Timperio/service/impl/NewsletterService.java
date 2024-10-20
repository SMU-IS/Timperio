package com.Timperio.service.impl;

import org.springframework.http.ResponseEntity;

import com.Timperio.dto.NewsletterRequestDTO;
import com.Timperio.dto.NewsletterResponseDTO;

public interface NewsletterService {
    ResponseEntity<String> sendNewsletter(NewsletterRequestDTO newsletterRequestDTO);
}
