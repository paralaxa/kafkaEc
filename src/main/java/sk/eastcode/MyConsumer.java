package sk.eastcode;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class MyConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "0.0.0.0:9092,0.0.0.0:9093,0.0.0.0:9094");
        props.put("group.id", "my_consumer_group");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
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
//todo
        //kafka semantic (commit at least once, ot most one, exactly once)
        //co je vyhoda eventov (viac konzumerov moze citat domenovy event, ie u nas log aj consumer)
        //alebo vyhodnocovanie abusive userov, kym processing pokracuje dalej, roundtrip streaming
        //kafka transactions
        //more consumers than partitions
        //add 4 consumers to 3 partitions topic and kill one cunsimer (will zookeeper add the new one instaed?)
        //show rebalance (ie adding and removing consumers within same group)
        @Override
        public void run() {
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Collections.singletonList("gajaka2"));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
                            record.key(), record.value());
            }
        }
    }


}