package in.rauf.kafka.producer;

import in.rauf.kafka.model.Packet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class Producer {

  private final KafkaTemplate<String, Packet> kafkaTemplate;
  private final String                        dataTopic;

  public Producer(KafkaTemplate<String, Packet> kafkaTemplate,
      @Value("${packet.topic}") String topic) {
    this.kafkaTemplate = kafkaTemplate;
    this.dataTopic = topic;
  }

  public void send(Packet packet) {
    Message<Packet> msg = MessageBuilder
        .withPayload(packet)
        .setHeader(KafkaHeaders.TOPIC, dataTopic)
        //.setHeader(KafkaHeaders.MESSAGE_KEY, "999")
        //.setHeader(KafkaHeaders.PARTITION_ID, 0)
        .setHeader("X-Custom-Header", "This is a custom header")
        .build();

    kafkaTemplate.send(msg);
  }
}
