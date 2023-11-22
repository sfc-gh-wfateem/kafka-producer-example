package com.snowflake.support;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        /*
         Press Opt+Enter with your caret at the highlighted text to see how
         IntelliJ IDEA suggests fixing it.
        */
        System.out.printf("Starting Kafka Producer");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 100; i++) {
            ProducerRecord pRecord = new ProducerRecord<String, String>("my-topic-1", Integer.toString(i), Integer.toString(i));
            try {
                RecordMetadata recordMetadata = (RecordMetadata) producer.send(pRecord).get();
                System.out.println(MessageFormat.format("Sent message to topic {0}, partition-{1}, with offset {2}",
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset()));
            }catch(Exception e){
                System.out.println("Failed to send message: " + i);
                System.out.println(e.getMessage());
                printStackTraceError(e.getStackTrace());
            }
            /*
            We can produce asynchronously using:
            producer.send(pRecord, new Callback() {
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if(e != null) {
                                e.printStackTrace();
                            } else {
                                System.out.println("The offset of the record we just sent is: " + metadata.offset());
                            }
                        }
                    });
            */
        }

        producer.close();

    }

    private static void printStackTraceError(StackTraceElement[] stackTrace){
        for (StackTraceElement e: stackTrace){
            System.out.println(e.toString());
        }
    }

}