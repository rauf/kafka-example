package in.rauf.kafka.config;

import in.rauf.kafka.constant.Constants;
import in.rauf.kafka.model.Packet;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

  private final String bootstrapServers;

  public KafkaProducerConfig(
      @Value("${kafka.bootstrap-servers}") String bootstrapServers) {
    this.bootstrapServers = bootstrapServers;
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
    return props;
  }

  @Bean(name = Constants.PACKET_PRODUCER)
  public KafkaTemplate<String, Packet> packetKafkaTemplate() {
    KafkaTemplate<String, Packet> kafkaTemplate = new KafkaTemplate(
        new DefaultKafkaProducerFactory<String, Packet>(producerConfigs()));
    //kafkaTemplate.setProducerListener(listener);
    return kafkaTemplate;
  }

}
