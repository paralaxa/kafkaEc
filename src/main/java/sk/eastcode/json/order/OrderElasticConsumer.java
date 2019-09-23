package sk.eastcode.json.order;

import org.apache.http.HttpHost;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OrderElasticConsumer {

    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.deserializer", LongDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("group.id", "order_json_elastic_indexer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(props);

        try {
            consumer.subscribe(Collections.singleton("json_orders"));

            for (int i = 0; true; i++) {
                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<Long, String> record : records) {
                    IndexRequest request = new IndexRequest("orders")
                            .id(String.valueOf(i))
                            .source(record.value(), XContentType.JSON);

                    client.index(request, RequestOptions.DEFAULT);
                    System.out.println("Indexed: " + record.value());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            consumer.close();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
