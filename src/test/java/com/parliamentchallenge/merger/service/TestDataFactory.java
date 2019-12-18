package com.parliamentchallenge.merger.service;

import com.parliamentchallenge.merger.service.jaxb.Information;
import com.parliamentchallenge.merger.service.jaxb.InformationsList;
import com.parliamentchallenge.merger.service.jaxb.Speaker;
import com.parliamentchallenge.merger.service.jaxb.Speech;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class TestDataFactory {

    static Speaker aSpeaker() {
        Speaker speaker = new Speaker();
        speaker.setName("TESTSPEAKER");
        speaker.setParty("M");
        speaker.setConstituency("Skåne läns norra och östra");
        speaker.setImageUrl("http://data.riksdagen.se/filarkiv/bilder/ledamot/ef84acf4-4f56-45d9-9951-1c8ba4098302_80.jpg");
        InformationsList informationsList = new InformationsList();
        Information information = new Information();
        information.setCode("Officiell e-postadress");
        information.setInformation("hans.wallmark[på]riksdagen.se");
        informationsList.setInformations(asList(information));
        speaker.setInformations(informationsList);
        return speaker;
    }

    static Speech aSpeech() {
        return getSpeech("H70949", "226", "0322827326923", "2019-12-11", "SPEECH_1");
    }

    public static Speech aSpeechWithSpeaker() {
        return aSpeech().addSpeaker(aSpeaker());
    }

    private static Speech getSpeech(String docId, String speechNumber, String speakerId, String speechDate, String subject) {
        Speech speech = new Speech();
        speech.setDocId(docId);
        speech.setSpeechNumber(speechNumber);
        speech.setSpeaker(null);
        speech.setSpeakerId(speakerId);
        speech.setSpeechDate(speechDate);
        speech.setSubject(subject);
        return speech;
    }

    static List<Speech> aSpeechesList() {
        Speech speech1 = aSpeech();
        Speech speech2 = getSpeech("H70949", "225", "123", "2019-12-11", "SPEECH_2");
        return asList(speech1, speech2);
    }

    public static List<Speech> aSpeechesWithSpeakerList() {
        return aSpeechesList().stream().map(s -> s.addSpeaker(aSpeaker())).collect(toList());
    }
}
