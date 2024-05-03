package Kafka;

import org.apache.kafka.clients.producer.*;

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
        String topicName = "my-topic";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        try {
            for (int i = 0; i < 10; i++) {
                String message = "Message " + i;
                producer.send(new ProducerRecord<>(topicName, Integer.toString(i), message));
                System.out.println("Sent:" + message);
            }
        } finally {
            producer.close();
        }
    }
}
