package sk.eastcode.avro.order;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import sk.eastcode.OrderUtils;
import sk.eastcode.model.Order;

import java.util.Properties;

public class OrderProducer {

    public static final int STORE_COUNT = 3;

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", LongSerializer.class.getName());
        props.put("value.serializer", KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", "http://localhost:8081");

        Producer<Long, Order> producer = new KafkaProducer<>(props);

        try {
            for (long i = 0; true; i++) {
                Order order = Order.newBuilder()
                        .setId(i)
                        .setStoreId(i % STORE_COUNT)
                        .setItem(OrderUtils.getRandomStoreItem())
                        .build();

                ProducerRecord<Long, Order> producerRecord =
                        new ProducerRecord<>("avro_orders", order.getStoreId(), order);

                producer.send(producerRecord);

                System.out.println("producing: " + order);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
