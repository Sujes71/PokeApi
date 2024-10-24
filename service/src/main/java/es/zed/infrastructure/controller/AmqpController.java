package es.zed.infrastructure.controller;

import es.zed.common.AbstractEvent;
import es.zed.controller.AbstractAmqpController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * Amqp controller.
 */
@RestController
@Slf4j
public class AmqpController extends AbstractAmqpController<AbstractEvent<?>> {

  public AmqpController(RabbitTemplate requestTemplate, @Value("${producer.exchange.name}") String producerExchangeName) {
    super(requestTemplate, producerExchangeName);
  }
}
