package com.parliamentchallenge.merger.service.jaxb;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
public class Speaker {

    public static Speaker NO_SPEAKER = new Speaker();

    @XmlElement(name = "efternamn")
    private String name;

    @XmlElement(name = "parti")
    private String party;

    @XmlElement(name = "valkrets")
    private String constituency;

    @XmlElement(name = "bild_url_80")
    private String imageUrl;

    @XmlElement(name = "personuppgift")
    private InformationsList informations;

    public String getEmail() {
        return informations != null ? informations.getEmail() : null;
    }

}
