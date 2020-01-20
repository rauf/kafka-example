package in.rauf.kafka.cron;

import in.rauf.kafka.model.Packet;
import in.rauf.kafka.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DataCron {

  private final Producer producer;

  @Scheduled(fixedRate = 2000, initialDelay = 2000)
  public void cron() {
    producer.send(new Packet());
  }
}
