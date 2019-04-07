package sk.eastcode.avro.order;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongSerializer;
import sk.eastcode.model.Order;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OrderConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my_consumer_group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", LongSerializer.class.getName());
        props.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8081");
        props.setProperty("specific.avro.reader", "true");

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
            KafkaConsumer<Long, Order> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList("order"));
            while (true) {
                ConsumerRecords<Long, Order> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<Long, Order> record : records)
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
                            record.key(), record.value());
            }
        }
    }


}