package sk.eastcode;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class StringProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String value = "EASTCODE IS THE BEST!";

            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("string-messages", null, value);

            producer.send(producerRecord);

            System.out.println("Produced: " + value);
        }

        producer.close();
    }
}
