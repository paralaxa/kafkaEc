package sk.eastcode.json.order;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class OrderProducer {

    public static final int STORE_COUNT = 3;

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", LongSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        Producer<Long, String> producer = new KafkaProducer<>(props);
        Gson gson = new Gson();
        int i = 0;
        try {
            while (true) {
                i++;
                long storeId = (i % STORE_COUNT);
                Order  order = new Order((long)i, storeId, "item_number"+i);
                ProducerRecord<Long, String> producerRecord = new ProducerRecord<>
                        ("order", storeId, gson.toJson(order));
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
