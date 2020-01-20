package in.rauf.kafka.config;

import in.rauf.kafka.constant.Constants;
import in.rauf.kafka.model.Packet;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

  private final String bootstrapServers;
  private final String packetGroupId;
  private final String autoOffsetResetConfig;

  public KafkaConsumerConfig(
      @Value("${kafka.bootstrap-servers}") String bootstrapServers,
      @Value("${packet.consumer.groupId}") String packetGroupId,
      @Value("${kafka.auto.offset.reset.config}") String autoOffsetResetConfig) {
    this.bootstrapServers = bootstrapServers;
    this.packetGroupId = packetGroupId;
    this.autoOffsetResetConfig = autoOffsetResetConfig;
  }

  @Bean
  public Map<String, Object> packetConsumerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, packetGroupId);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetResetConfig);
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "default-clientId");
    return props;
  }

  @Bean(name = Constants.PACKET_CONSUMER)
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Packet>> packetListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Packet> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(
        new DefaultKafkaConsumerFactory<>(packetConsumerConfig(),
            new StringDeserializer(),
            new JsonDeserializer<>(Packet.class)));
    return factory;
  }


}
