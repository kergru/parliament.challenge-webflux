package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Speech;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeech;
import static com.parliamentchallenge.merger.service.TestDataFactory.aSpeechesList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.synchronoss.cloud.nio.multipart.util.IOUtils.inputStreamAsString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpeechServiceTest {

    private MockWebServer mockWebServer = new MockWebServer();

    private SpeechService speechService = new SpeechService(WebClient.create(mockWebServer.url("/").toString()));

    private FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void find_one_should_return_speech_mono() throws IOException {
        Resource anforande = resourceLoader.getResource("classpath:mocks/anforande.xml");
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                        .setBody(inputStreamAsString(anforande.getInputStream(), "UTF-8"))
        );

        Speech speech = speechService.findOne("H70949-226").block();

        assertEquals(speech, aSpeech());
    }


    @Test
    void find_should_return_list_of_speech_mono() throws IOException {
        Resource anforande = resourceLoader.getResource("classpath:mocks/anforandelista.xml");
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_XML_VALUE)
                        .setBody(inputStreamAsString(anforande.getInputStream(), "UTF-8"))
        );

        List<Speech> speeches = speechService.find(null, null).block();

        assertEquals(speeches, aSpeechesList());
    }
}