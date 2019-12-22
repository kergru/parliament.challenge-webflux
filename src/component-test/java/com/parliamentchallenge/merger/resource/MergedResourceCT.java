package com.parliamentchallenge.merger.resource;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import reactor.blockhound.BlockHound;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"parliament.api.url=http://localhost:8089"})
@AutoConfigureMockMvc
class MergedResourceCT {

    private WireMockServer wireMockServer;

    private WireMock wireMock;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void init() {
        BlockHound.install();
    }


    @BeforeEach
    void configureSystemUnderTest() {
        this.wireMockServer = new WireMockServer(options()
                .port(8089)
                .usingFilesUnderClasspath("wiremock"));
        this.wireMockServer.start();

        wireMock = new WireMock("localhost", 8089);

        setupWireMock();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    public void setupWireMock() {

        wireMock.register(get(urlEqualTo("/anforandelista/?sz=10")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/xml;charset=UTF-8")
                .withBodyFile("xml/anforandelista.xml")
        ));

        wireMock.register(get(urlPathMatching("/anforande/.*")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/xml;charset=UTF-8")
                .withBodyFile("xml/anforande.xml")
        ));

        wireMock.register(get(urlPathMatching("/person/.*")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/xml;charset=UTF-8")
                .withBodyFile("xml/personlista.xml")
        ));
    }

    @Test
    void should_return_speech_with_speaker() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/speeches/H70949-226"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json;charset=UTF-8"))
                .andExpect(content().string("{" +
                        "\"uid\":\"H70949-226\"," +
                        "\"speechDate\":\"2019-12-11\"," +
                        "\"speechSubject\":\"SPEECH_1\"," +
                        "\"speakerName\":\"TESTSPEAKER\"," +
                        "\"speakerPoliticalAffiliation\":\"M\"," +
                        "\"speakerEmail\":\"hans.wallmark[på]riksdagen.se\"," +
                        "\"speakerConstituency\":\"Skåne läns norra och östra\"," +
                        "\"speakerImageUrl\":\"http://data.riksdagen.se/filarkiv/bilder/ledamot/ef84acf4-4f56-45d9-9951-1c8ba4098302_80.jpg\"," +
                        "\"_links\":{" +
                        "\"self\":{" +
                        "\"href\":\"http://localhost:8080/speeches/H70949-226\"" +
                        "}" +
                        "}" +
                        "}"));
    }

    @Test
    void should_return_speeches_with_speaker() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/speeches/search"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mvcResult.getAsyncResult();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("[" +
                        "{" +
                        "\"uid\":\"H70949-226\"," +
                        "\"speechDate\":\"2019-12-11\"," +
                        "\"speechSubject\":\"SPEECH_1\"," +
                        "\"speakerName\":\"TESTSPEAKER\"," +
                        "\"speakerPoliticalAffiliation\":\"M\"," +
                        "\"speakerEmail\":\"hans.wallmark[på]riksdagen.se\"," +
                        "\"speakerConstituency\":\"Skåne läns norra och östra\"," +
                        "\"speakerImageUrl\":\"http://data.riksdagen.se/filarkiv/bilder/ledamot/ef84acf4-4f56-45d9-9951-1c8ba4098302_80.jpg\"," +
                        "\"links\":[" +
                        "{" +
                        "\"rel\":\"self\"," +
                        "\"href\":\"http://localhost:8080/speeches/H70949-226\"" +
                        "}" +
                        "]" +
                        "}," +
                        "{" +
                        "\"uid\":\"H70949-225\"," +
                        "\"speechDate\":\"2019-12-11\"," +
                        "\"speechSubject\":\"SPEECH_2\"," +
                        "\"speakerName\":\"TESTSPEAKER\"," +
                        "\"speakerPoliticalAffiliation\":\"M\"," +
                        "\"speakerEmail\":\"hans.wallmark[på]riksdagen.se\"," +
                        "\"speakerConstituency\":\"Skåne läns norra och östra\"," +
                        "\"speakerImageUrl\":\"http://data.riksdagen.se/filarkiv/bilder/ledamot/ef84acf4-4f56-45d9-9951-1c8ba4098302_80.jpg\"," +
                        "\"links\":[" +
                        "{" +
                        "\"rel\":\"self\"," +
                        "\"href\":\"http://localhost:8080/speeches/H70949-225\"" +
                        "}" +
                        "]" +
                        "}" +
                        "]"));
    }
}