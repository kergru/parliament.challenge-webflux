package com.parliamentchallenge.merger.service.jaxb;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@XmlRootElement(name = "personuppgift")
@XmlAccessorType(XmlAccessType.FIELD)
public class InformationsList {

    @XmlElement(name = "uppgift")
    private List<Information> informations;

    public String getEmail() {
        Optional<Information> information = informations != null ? informations.stream().filter(i -> "Officiell e-postadress".equals(i.getCode())).findFirst() : Optional.empty();
        return information.isPresent() ? information.get().getInformation() : null;
    }
}
