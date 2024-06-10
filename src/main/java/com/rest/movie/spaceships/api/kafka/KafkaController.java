package com.rest.movie.spaceships.api.kafka;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

	private final KafkaProducer kafkaProducer;

	public KafkaController(KafkaProducer kafkaProducer) {
		this.kafkaProducer = kafkaProducer;
	}

	@PostMapping("/publish")
	public ResponseEntity<String> sendMessageToKafkaTopic(@RequestBody String message) {
		kafkaProducer.sendMessage("my-topic", message);
		return new ResponseEntity<>("Message sent to Kafka topic", HttpStatus.OK);
	}

}
