package com.example.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    @KafkaListener(topics = "${my.kafka.consumer.topic}")
    public void listen(ConsumerRecord<String, String> kafkaMessage) {
    	 System.out.println(String.format("#### -> Consumed message -> %s", kafkaMessage));
    	logger.info(String.format("Received data     = %s", kafkaMessage));
    }
}
