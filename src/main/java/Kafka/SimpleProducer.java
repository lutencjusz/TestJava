package Kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Producent Kafka
 * Wysyła 10 wiadomości do tematu "my-topic"
 *
 * Aby uruchomić Kafkę, należy w terminalu wpisać:
 * cd C:\Kafka\kafka_2.13-3.7.0
 * .\bin\windows\zookeeper-server-start.bat "C:\Kafka\kafka_2.13-3.7.0\config\zookeeper.properties"
 *
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
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "SimpleProducer");

        Producer<String, String> producer = new KafkaProducer<>(props);

//        try {
//            for (int i = 0; i < 10; i++) {
//                String message = "Message " + i;
//                producer.send(new ProducerRecord<>(topicName, Integer.toString(i), message));
//                System.out.println("Sent:" + message);
//            }
//        } finally {
//            producer.close();
//        }

        producer.send(new ProducerRecord<>(topicName, "testowy-klucz", "Komunikat testowy 1"));
        producer.close();
    }
}
