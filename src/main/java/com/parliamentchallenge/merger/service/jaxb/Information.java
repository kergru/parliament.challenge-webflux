package com.parliamentchallenge.merger.service.jaxb;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "uppgift")
@XmlAccessorType(XmlAccessType.FIELD)
public class Information {

    @XmlElement(name = "kod")
    private String code;

    @XmlElement(name = "uppgift")
    private String information;
}
