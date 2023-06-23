package com.travelbookingnotification.services.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerService {

    public ConsumerRecords<String, String> getMessagesFromProducer(String topic) {
        // TODO (daniel.lopezj): Move this props to a property file
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topic));

            return consumer.poll(Duration.ofMillis(100));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return (ConsumerRecords<String, String>) Collections.emptyMap();
    }
}
