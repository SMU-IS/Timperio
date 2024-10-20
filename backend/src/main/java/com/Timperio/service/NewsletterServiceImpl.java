package com.Timperio.service;

import com.Timperio.service.impl.NewsletterService;

import lombok.AllArgsConstructor;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import com.Timperio.dto.NewsletterRequestDTO;
import com.Timperio.dto.NewsletterResponseDTO;
import com.Timperio.config.MailChimpConstant;


@Service
@AllArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    @Autowired
    private MailChimpConstant mailChimpConstant;

    @Autowired
    private RestTemplate restTemplate;
    
    public ResponseEntity<String> sendNewsletter(NewsletterRequestDTO newsletterRequestDTO) {
        // NewsletterResponseDTO responseDTO = new NewsletterResponseDTO(newsletterRequestDTO.getEmail(), "success");
        // String hashedEmail = this.md5Encode(newsletterRequestDTO.getEmail());

        String auth = Base64.encodeBase64String(("user:" + this.mailChimpConstant.getAPI_KEY()).getBytes());
        
        System.out.println(auth);
        String datacenter = this.mailChimpConstant.getAPI_KEY().substring(this.mailChimpConstant.getAPI_KEY().lastIndexOf('-') + 1);
        // String mailchimpUrl = "https://" + datacenter + ".api.mailchimp.com/3.0/lists/" + this.mailChimpConstant.() + "/members/" + ;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + auth);

        HttpEntity<NewsletterRequestDTO> requestEntity = new HttpEntity<>(newsletterRequestDTO, headers);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://" + datacenter + ".api.mailchimp.com/3.0/ping", HttpMethod.GET, entity, String.class);

        System.out.println(exchange.getBody());
        // ResponseEntity<NewsletterResponseDTO> response = new ResponseEntity<>(exchange.getBody(), exchange.getStatusCode());
        
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange;
        } else {
            return ResponseEntity.status(exchange.getStatusCode()).body(null);
        }
    }
}

