package com.parliamentchallenge.merger.service.jaxb;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "personlista")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
public class SpeakersList {

    @XmlElement(name = "person")
    private List<Speaker> speakers;
}
