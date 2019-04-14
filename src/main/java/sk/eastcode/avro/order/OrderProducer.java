package sk.eastcode.avro.order;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import sk.eastcode.model.Order;

import java.util.Properties;

public class OrderProducer {

    public static final int STORE_COUNT = 3;

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", LongSerializer.class.getName());
        props.setProperty("value.serializer", KafkaAvroSerializer.class.getName());
        props.setProperty("schema.registry.url", "http://localhost:8081");
        Producer<Long, Order> producer = new KafkaProducer<>(props);
        int i = 0;
        try {
            while (true) {
                i++;
                long storeId = (i % STORE_COUNT);
                Order order = new Order((long)i, storeId, "item_number"+i);
                ProducerRecord<Long, Order> producerRecord = new ProducerRecord<>
                        ("order", storeId, order);
                producer.send(producerRecord);
                producer.flush();
                Thread.sleep(1000);
                System.out.println("producing : "+order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
