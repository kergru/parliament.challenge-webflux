package com.parliamentchallenge.merger.service.jaxb;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "anforandelista")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpeechesList {

    @XmlElement(name = "anforande")
    private List<Speech> speeches;

}