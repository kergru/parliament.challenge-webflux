package com.parliamentchallenge.merger.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
public class SpeechResponse extends RepresentationModel<SpeechResponse> {

    private String uid;

    private String speechDate;

    private String speechSubject;

    private String speakerName;

    private String speakerPoliticalAffiliation;

    private String speakerEmail;

    private String speakerConstituency;

    private String speakerImageUrl;

}
