package Kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Producent Kafka
 * Wysyła 10 wiadomości do tematu "my-topic"
 * <p>
 * Aby uruchomić Kafkę, należy w terminalu wpisać:
 * cd C:\Kafka\kafka_2.13-3.7.0
 * .\bin\windows\zookeeper-server-start.bat "C:\Kafka\kafka_2.13-3.7.0\config\zookeeper.properties"
 * <p>
 * w drugim terminalu:
 * cd C:\Kafka\kafka_2.13-3.7.0
 * .\bin\windows\kafka-server-start.bat "C:\Kafka\kafka_2.13-3.7.0\config\server.properties"
 */
public class SimpleProducer {
    public static void main(String[] args) {
        String topicName = "producer-example";
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "SimpleProducer");

        Producer<String, Product> producer = new KafkaProducer<>(props);

        try {
            for (int i = 0; i < 2; i++) {
                String message = "Message " + i;
                producer.send(new ProducerRecord<>(topicName, null, new Product(message,1))).get();
                System.out.println("Sent:" + message);
            }

            producer.send(new ProducerRecord<>(topicName, "testowy-klucz-2", new Product("Produkt testowy 1", 1.1))).get();
            producer.send(new ProducerRecord<>(topicName, "testowy-klucz-3", new Product("Produkt testowy 2",1.2)), (recordMetadata, e) -> {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.printf("Wysłano komunikat:(topic: %s, partition: %s, offset: %s) ", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            producer.close();
        }
    }
}
