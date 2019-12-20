package com.parliamentchallenge.merger.resource;

import com.parliamentchallenge.merger.service.MergerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeechWithSpeaker;
import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeechesWithSpeakerList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.hateoas.Link.REL_SELF;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = MergedResource.class)
class MergedResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MergerService mergerService;

    @Test
    void get_speech_should_return_speech_mono() {
        doReturn(Mono.just(aSpeechWithSpeaker())).when(mergerService).findOneAndMerge("1");

        webTestClient.get()
                .uri("/speeches/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/hal+json;charset=UTF-8")
                .expectBody(MergedSpeechResponse.class)
                .value(speechResponse -> speechResponse.getUid(), equalTo("H70949-226"))
                .value(speechResponse -> speechResponse.getLink(REL_SELF).get().getHref(), equalTo("http://localhost:8080/speeches/H70949-226"));
    }

    @Test
    void get_speeches_should_return_speech_flux() {
        doReturn(Flux.fromIterable(aSpeechesWithSpeakerList())).when(mergerService).findAndMerge("x", "y");

        webTestClient.get()
                .uri("/speeches/search?speakerId=x&party=y")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/json;charset=UTF-8")
                .expectBodyList(MergedSpeechResponse.class)
                .value(speechResponse -> assertSpeeches(speechResponse));
    }

    private void assertSpeeches(List<MergedSpeechResponse> speechResponse) {
        assertTrue(speechResponse.size() == 2);
        assertEquals(speechResponse.get(0).getUid(), "H70949-226");
        assertEquals(speechResponse.get(0).getLink(REL_SELF).get().getHref(), "http://localhost:8080/speeches/H70949-226");
        assertEquals(speechResponse.get(1).getUid(), "H70949-225");
        assertEquals(speechResponse.get(1).getLink(REL_SELF).get().getHref(), "http://localhost:8080/speeches/H70949-225");
    }
}