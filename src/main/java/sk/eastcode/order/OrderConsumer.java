package sk.eastcode.order;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "0.0.0.0:9092");
        props.put("group.id", "my_consumer_group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new MyConsumerThread(props));
            t.start();
        }
    }

    private static class MyConsumerThread implements Runnable {
        private final Properties props;

        public MyConsumerThread(Properties props) {
            this.props = props;
        }
        public void run() {
            KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList("order"));
            while (true) {
                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<Long, String> record : records)
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
                            record.key(), record.value());
            }
        }
    }


}