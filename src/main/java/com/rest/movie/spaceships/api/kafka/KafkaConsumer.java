package com.rest.movie.spaceships.api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
	

    @KafkaListener(topics = "spaceship-topic", groupId = "spaceship-group")
    public void listen(String message) {
        log.info("Received message: " + message);
    }

}
