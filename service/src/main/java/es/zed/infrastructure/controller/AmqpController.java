package es.zed.infrastructure.controller;

import es.zed.controller.AbstractAmqpController;
import es.zed.domain.intput.PokeApiHandlerPort;
import es.zed.event.common.AbstractEvent;
import es.zed.event.pokedb.AbilityCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * Amqp controller.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AmqpController extends AbstractAmqpController<AbstractEvent> {

  /**
   * Handler.
   */
  private final PokeApiHandlerPort handler;

  /**
   * Consumer of Ability created event.
   *
   * @param event event.
   */
  public void consume(AbilityCreatedEvent event) {
    log.info("Event {} is being consumed from {}.", event.getTypeId(), event.getContext());
    handler.handleAbilityCreatedEvent(event);
  }
}
