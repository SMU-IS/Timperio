package com.Timperio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailChimpConstant {
     
    @Value("${MAILCHIMP_API_KEY}")
    private String API_KEY;

    public String getAPI_KEY() {
        return this.API_KEY;
    }
}
