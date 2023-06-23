package com.travelbookingnotification;

import com.travelbookingnotification.services.email.EmailNotification;
import com.travelbookingnotification.services.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class TravelBookingNotificationApplication {

    public static void main(String[] args) {
        KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
        String topic = args[0];

        boolean noRecordFound = true;
        while (noRecordFound) {
            ConsumerRecords<String, String> records = kafkaConsumerService.getMessagesFromProducer(topic);
            for (ConsumerRecord<String, String> record : records) {
                if (record != null) {
                    sendEmail(record.value());
                    noRecordFound = false;
                }
            }
        }
    }

    public static void sendEmail(String email) {
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.sendEmailNotification(email);
    }
}
