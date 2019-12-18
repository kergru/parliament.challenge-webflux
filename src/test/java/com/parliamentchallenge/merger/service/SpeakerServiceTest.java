package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speaker;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeaker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.synchronoss.cloud.nio.multipart.util.IOUtils.inputStreamAsString;

class SpeakerServiceTest {

    private MockWebServer mockWebServer = new MockWebServer();

    private SpeakerService speakerService = new SpeakerService(WebClient.create(mockWebServer.url("/").toString()));

    private FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void get_speaker_by_intressentid_should_return_speaker_mono() throws IOException {
        Resource personlista = resourceLoader.getResource("classpath:mocks/personlista.xml");
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                        .setBody(inputStreamAsString(personlista.getInputStream(), "UTF-8"))
        );

        Speaker speaker = speakerService.findOne("0980681611418").block();

        assertSpeaker(speaker, aSpeaker());
    }

    private void assertSpeaker(Speaker expected, Speaker actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getParty(), actual.getParty());
        assertEquals(expected.getConstituency(), actual.getConstituency());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getEmail(), actual.getEmail());
    }
}