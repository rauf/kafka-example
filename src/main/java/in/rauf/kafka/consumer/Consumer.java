package in.rauf.kafka.consumer;

import in.rauf.kafka.constant.Constants;
import in.rauf.kafka.model.Packet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Consumer {

  @KafkaListener(
      topics = "${packet.topic}",
      containerFactory = Constants.PACKET_CONSUMER,
      clientIdPrefix = "overridden-clientId")
  public void consume1(
      @Payload Packet packet,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
      //@Header(KafkaHeaders.MESSAGE_KEY) String messageKey,
      @Header(KafkaHeaders.GROUP_ID) String groupId,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) long partitionId,
      @Header(KafkaHeaders.OFFSET) long offset,
      @Header("X-Custom-Header") String customHeader) {
    log.info("Topic: {}, Partition Id: {}, Offset: {}, Payload: {}, GroupId: {}, Custom Header: {}",
        topic, partitionId, offset, packet, groupId, customHeader);
  }

//  // working
//  @KafkaListener(topics = "${data-topic}", groupId = "data_group", clientIdPrefix = "myapp-client")
//  public void consume(
//      @Payload Packet packet,
//      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//      //@Header(KafkaHeaders.MESSAGE_KEY) String messageKey,
//      @Header(KafkaHeaders.GROUP_ID) String groupId,
//      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) long partitionId,
//      @Header(KafkaHeaders.OFFSET) long offset,
//      @Header("X-Custom-Header") String customHeader) {
//    log.info("Topic: {}, Partition Id: {}, Offset: {}, Payload: {}, GroupId: {}, Custom Header: {}",
//        topic, partitionId, offset, packet, groupId, customHeader);
//  }

}
