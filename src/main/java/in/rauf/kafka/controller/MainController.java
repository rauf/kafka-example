package in.rauf.kafka.controller;

import in.rauf.kafka.model.Packet;
import in.rauf.kafka.producer.Producer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

  private final Producer producer;

  public MainController(Producer producer) {
    this.producer = producer;
  }

  @GetMapping("/kafka")
  public void send() {

    for (int i = 0; i < 3; i++) {
      producer.send(new Packet());
    }
  }

}
