package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class MergerService {

    private final SpeechService speechService;

    private final SpeakerService speakerService;

    public Mono<Speech> findOneAndMerge(String id) {

        return speechService.findOne(id).flatMap(speech -> Mono.just(speech)
                .zipWith(speakerService.findOne(speech.getSpeakerId()), Speech::addSpeaker));
    }

    public Flux<Speech> findAndMerge(String speakerId, String party) {
        return speechService.find(speakerId, party)
                .flatMapIterable(i -> i)
                .flatMap(speech -> Mono.just(speech)
                        .zipWith(speakerService.findOne(speech.getSpeakerId()), Speech::addSpeaker));
    }

}
