package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeaker;
import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeech;
import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeechesList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MergerServiceTest {

    @Mock
    private SpeakerService speakerService;

    @Mock
    private SpeechService speechService;

    @InjectMocks
    private MergerService mergerService;

    @Test
    void findOneAndMerge_should_return_speech_with_speaker() {
        doReturn(Mono.just(aSpeech())).when(speechService).findOne("xy");
        doReturn(Mono.just(aSpeaker())).when(speakerService).findOne(any());

        Mono<Speech> speechMono = mergerService.findOneAndMerge("xy");
        Speech speech = speechMono.block();

        assertEquals(TestDataFactory.aSpeechWithSpeaker(), speech);
    }

    @Test
    void findAndMerge_should_return_speeches_with_speaker() {
        doReturn(Mono.just(aSpeechesList())).when(speechService).find(null, null);
        doReturn(Mono.just(aSpeaker())).when(speakerService).findOne(any());

        Flux<Speech> speechesFlux = mergerService.findAndMerge(null, null);
        List<Speech> speeches = speechesFlux.collectList().block();

        assertEquals(TestDataFactory.aSpeechesWithSpeakerList(), speeches);
    }

}