package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpeechServiceTest {

    @Autowired
    private SpeechService speechService;

    @Test
    void testXml() {

        Mono<List<Speech>> speechesMono = speechService.find(null, null);

        List<Speech> speeches = speechesMono.block();
        assertNotNull(speeches);
        assertEquals(10, speeches.size());
    }
}