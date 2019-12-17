package com.parliamentchallenge.merger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MergerConfig {
    @Bean
    public WebClient webClient() {
        return WebClient
                .builder()
                .baseUrl("http://data.riksdagen.se")
                .build();
    }
}

