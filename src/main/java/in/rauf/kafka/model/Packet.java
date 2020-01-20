package in.rauf.kafka.model;

import java.util.Random;
import java.util.UUID;
import lombok.Data;

@Data
public class Packet {

  private final int    id   = new Random().nextInt();
  private final String name = UUID.randomUUID().toString();

}
