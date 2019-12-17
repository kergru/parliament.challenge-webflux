package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speaker;
import com.parliamentchallenge.merger.service.jaxb.SpeakersList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;


@Component
@RequiredArgsConstructor
public class SpeakerService {

    private final WebClient webClient;

    public Mono<Speaker> findOne(String intressentId) {
        return webClient.get()
                .uri("/personlista/?iid=" + intressentId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                .accept(MediaType.TEXT_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(SpeakersList.class)
                .map(list -> list.getSpeakers().get(0));
    }
}
