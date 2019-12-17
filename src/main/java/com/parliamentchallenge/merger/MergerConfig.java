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
//                .exchangeStrategies(ExchangeStrategies.builder().codecs((configurer) -> {
//
//                    configurer.defaultCodecs().jaxb2Encoder(new Jaxb2XmlEncoder());
//                    configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
//                    //configurer.customCodecs().encoder(new Jackson2JsonEncoder(new ObjectMapper(), TEXT_HTML));
//                    //configurer.customCodecs().decoder(new Jackson2JsonDecoder(new ObjectMapper(), TEXT_HTML));
//                }).build())
                .build();
    }
}

