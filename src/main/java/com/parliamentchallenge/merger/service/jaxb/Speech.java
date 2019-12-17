package com.parliamentchallenge.merger.service.jaxb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "anforande")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
public class Speech {

    @XmlElement(name = "dok_id")
    private String docId;

    @XmlElement(name = "anforande_nummer")
    private String speechId;

    @XmlElement(name = "dok_datum")
    private String speechDate;

    @XmlElement(name = "avsnittsrubrik")
    private String subject;

    @XmlElement(name = "intressent_id")
    private String speakerId;

    private Speaker speaker;

    public Speech addSpeaker(Speaker speaker) {
        this.speaker = speaker;
        return this;
    }

    public String getUid() {
        return docId + "-" + speechId;
    }
}