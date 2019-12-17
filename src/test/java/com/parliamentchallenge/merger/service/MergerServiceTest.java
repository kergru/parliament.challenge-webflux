package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MergerServiceTest {

    @Autowired
    private MergerService mergerService;

    @Test
    void merge_should_return_speeches_with_speaker() {

        Flux<Speech> speechesFlux = mergerService.findAndMerge(null, null);
        List<Speech> speeches = speechesFlux.collectList().block();

        assertNotNull(speeches);
        assertEquals(10, speeches.size());
    }

}