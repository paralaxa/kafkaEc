package sk.eastcode.json.order;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import sk.eastcode.OrderUtils;

import java.util.Properties;

public class OrderProducer {

    public static void main(String[] args) throws Exception {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", LongSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

        Producer<Long, String> producer = new KafkaProducer<>(props);

        try {
            while (true) {
                Order order = OrderUtils.getRandomOrder();

                ProducerRecord<Long, String> producerRecord =
                        new ProducerRecord<>("json_orders", order.getStoreId(), gson.toJson(order));

                producer.send(producerRecord);

                System.out.println("producing: " + order);

                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
