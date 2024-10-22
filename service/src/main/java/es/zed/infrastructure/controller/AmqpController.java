package es.zed.infrastructure.controller;

import es.zed.common.AbstractEvent;
import es.zed.controller.AbstractAmqpController;
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

}
