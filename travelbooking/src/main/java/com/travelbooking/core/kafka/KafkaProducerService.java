package com.travelbooking.core.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaProducerService {

    public String sendMessageToTopic(String topicName, String message) {
        String status = "";
        try {
            // TODO (daniel.lopezj): Move props to a separate property file
            Producer<String, String> producer;
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>(topicName, message));
            producer.close();
            status = "success";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return status;
    }
}
