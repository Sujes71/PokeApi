package es.zed.infrastructure.controller;

import es.zed.common.AbstractEvent;
import es.zed.config.AbstractKafkaController;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka controller.
 */
@RestController
public class KafkaController extends AbstractKafkaController<AbstractEvent<?>> {

  /**
   * Constructor.
   *
   * @param template template.
   */
  protected KafkaController(KafkaTemplate<String, AbstractEvent<?>> template) {
    super(template);
  }

}
