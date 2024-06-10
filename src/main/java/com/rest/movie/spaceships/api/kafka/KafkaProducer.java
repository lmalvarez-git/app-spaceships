package com.rest.movie.spaceships.api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class KafkaProducer {
	
	   private final KafkaTemplate<String, String> kafkaTemplate;

	    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	    }

	    public void sendMessage(String topic, String message) {
			final CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

			future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.info(result.toString());
                } else {
                    log.error("Error send topic-message");
                }
            });
	    }

}
