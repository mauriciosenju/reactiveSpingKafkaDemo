package com.reactivespring.kafkademo.reactivespringkafka.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.kafka.receiver.KafkaReceiver;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class kafkaService {

    @Autowired
    private KafkaReceiver<String, String> kafkaReceiver;

    private ConnectableFlux<ServerSentEvent<String>> eventPublisher;

    @PostConstruct
    public void init() {
        eventPublisher = kafkaReceiver.receive()
                .map(consumerRecord -> ServerSentEvent.builder(consumerRecord.value()).build())
                .publish();
        eventPublisher.connect();
    }

    public ConnectableFlux<ServerSentEvent<String>> getEventPublisher() {
        return eventPublisher;
    }

}