package com.reactivespring.kafkademo.reactivespringkafka.Controller;

import com.reactivespring.kafkademo.reactivespringkafka.Service.kafkaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactivespring.kafkademo.reactivespringkafka.Entity.count;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

//import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class reactiveController {

    @Autowired
    private kafkaService kafkaService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(value = "/counter/{id}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<count>> streamCounterEvents(@PathVariable("id") String id) {
        // Flux<ServerSentEvent<count>> heartbeatStream =
        // Flux.interval(Duration.ofSeconds(20))
        // .take(20)
        // .map(this::toHeartBeatServerSentEvent);

        return kafkaService.getEventPublisher()
                .doOnNext(c -> System.out.println(c))
                .map(stringServerSentEvent -> jsonStrToCount(stringServerSentEvent.data()))
                .filter(c -> c != null)
                .doOnNext(c -> System.out.println(c))
                .filter(c -> c.getCountId().equals(id))
                .map(c -> this.counterToServerSentEvent(c));
    }

    private ServerSentEvent<count> counterToServerSentEvent(count c) {
        return ServerSentEvent.<count>builder()
                .data(c)
                .build();
    }

    // private ServerSentEvent<count> toHeartBeatServerSentEvent(Long tick) {
    // Long countId = (long) 01;
    // return counterToServerSentEvent(new count(countId, "Heart-Beat" + tick));
    // }

    private count jsonStrToCount(String jsonStr) {
        count c = null;
        try {
            c = objectMapper.readValue(jsonStr, count.class);
        } catch (IOException ex) {
            System.out.println("parsing exception " + ex);
            return null;
        }

        return c;
    }

}