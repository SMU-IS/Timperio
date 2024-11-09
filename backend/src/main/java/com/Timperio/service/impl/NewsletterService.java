package com.Timperio.service.impl;

import org.springframework.http.ResponseEntity;

import com.Timperio.dto.NewsletterCampaignContentDTO;
import com.Timperio.dto.NewsletterRequestDTO;
import com.Timperio.dto.NewsletterResponseDTO;

public interface NewsletterService {
    ResponseEntity<String> healthCheck();

    ResponseEntity<String> getCampaigns();

    ResponseEntity<String> setCampaignContent(NewsletterCampaignContentDTO newsletterCampaignContentDTO);

    ResponseEntity<String> sendNewsletter(NewsletterRequestDTO newsletterRequestDTO);
}