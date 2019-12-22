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

import static com.parliamentchallenge.merger.service.jaxb.Speaker.NO_SPEAKER;
import static org.springframework.util.StringUtils.isEmpty;


@Component
@RequiredArgsConstructor
public class SpeakerService {

    private final WebClient webClient;

    public Mono<Speaker> findOne(String speakerId) {
        if (isEmpty(speakerId)) {
            return Mono.just(NO_SPEAKER);
        }
        return webClient.get()
                .uri("/person/" + speakerId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                .accept(MediaType.TEXT_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(SpeakersList.class)
                .map(list -> list.getSpeakers().get(0));
    }
}
