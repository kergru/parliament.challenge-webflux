package com.parliamentchallenge.merger.resource;

import com.parliamentchallenge.merger.service.MergerService;
import com.parliamentchallenge.merger.service.jaxb.Speech;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ResponseBody
@RequestMapping("/speeches")
@RequiredArgsConstructor
@RestController
public class MergedResource {

    private final MergerService mergerService;

    @GetMapping("/{id}")
    public Mono<SpeechResponse> getSpeech(@PathVariable String id) {
        Mono<Speech> speech = mergerService.findOneAndMerge(id);
        return speech.map(this::mapToResponse);
    }

    @GetMapping("/search")
    public Flux<SpeechResponse> search(
            @RequestParam(name = "speakerId", required = false) String speakerId, @RequestParam(name = "party", required = false) String party) {
        Flux<Speech> speeches = mergerService.findAndMerge(speakerId, party);
        return speeches.map(this::mapToResponse);
    }

    private SpeechResponse mapToResponse(Speech speech) {
        return SpeechResponse.builder()
                .uid(speech.getUid())
                .speechDate(speech.getSpeechDate())
                .speechSubject(speech.getSubject())
                .speakerName(speech.getSpeaker().getName())
                .speakerPoliticalAffiliation(speech.getSpeaker().getParty())
                .speakerConstituency(speech.getSpeaker().getConstituency())
                .speakerEmail(speech.getSpeaker().getEmail())
                .speakerImageUrl(speech.getSpeaker().getImageUrl())
                .build()
                .add(new Link("http://localhost:8080/speeches/" + speech.getUid(), Link.REL_SELF));
    }
}
