package sk.eastcode;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class MyProducer {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "0.0.0.0:9092,0.0.0.0:9093,0.0.0.0:9094");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        Producer<String, String> producer = new KafkaProducer<>(props);
        try {
            while (true) {

                ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>
                        ("gajaka2", "key","EASTCODE IS THE BEST");
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e!=null){
                            e.printStackTrace();
                        };
                    }
                });
                producer.flush();
                Thread.sleep(1000);
                System.out.println("producing");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            producer.close();
        }
    }
}
