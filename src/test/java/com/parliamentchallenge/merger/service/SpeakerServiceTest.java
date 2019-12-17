package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speaker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpeakerServiceTest {

    @Autowired
    private SpeakerService speakerService;

    @Test
    void get_speaker_by_intressentid_should_return_speaker_mono() {

        Speaker speaker = speakerService.findOne("0980681611418").block();

        assertNotNull(speaker);
        assertEquals("hans.wallmark[på]riksdagen.se", speaker.getEmail());
    }
}