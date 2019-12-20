package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import com.parliamentchallenge.merger.service.jaxb.SpeechesList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpeechService {

    private final WebClient webClient;

    public Mono<List<Speech>> find(String speakerId, String party) {
        String query = getQuery(speakerId, party);
        return webClient.get()
                .uri("/anforandelista/?" + query)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                .accept(MediaType.TEXT_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(SpeechesList.class)
                .map(speechesList -> speechesList.getSpeeches() != null ? speechesList.getSpeeches() : Collections.<Speech>emptyList());
    }

    private String getQuery(String speakerId, String party) {
        StringBuilder query = new StringBuilder("sz=10");
        if (party != null) {
            query.append("&parti=" + party);
        }
        if (speakerId != null) {
            query.append("&iid=" + speakerId);
        }
        return query.toString();
    }

    public Mono<Speech> findOne(String id) {
        return webClient.get()
                .uri("/anforandelista/anforande/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML_VALUE)
                .accept(MediaType.TEXT_XML)
                .acceptCharset(Charset.forName("UTF-8"))
                .retrieve()
                .bodyToMono(SpeechesList.class)
                .map(speechesList -> speechesList.getSpeeches() != null ? speechesList.getSpeeches().get(0) : null);
    }
}
