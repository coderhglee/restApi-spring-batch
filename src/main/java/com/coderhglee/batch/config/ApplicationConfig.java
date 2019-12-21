package com.coderhglee.batch.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static java.time.Duration.ofSeconds;

@Configuration
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setReadTimeout(ofSeconds(60))
                .setConnectTimeout(ofSeconds(60))
                .build();
    }
}
