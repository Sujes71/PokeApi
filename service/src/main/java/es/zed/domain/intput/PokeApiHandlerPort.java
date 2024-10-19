package es.zed.domain.intput;

import es.zed.event.pokedb.AbilityCreatedEvent;

/**
 * poke db handler port.
 */
public interface PokeApiHandlerPort {

  /**
   * AbilityCreatedEvent handler.
   *
   * @param abilityCreatedEvent ability created event.
   */
  void handleAbilityCreatedEvent(AbilityCreatedEvent abilityCreatedEvent);

}
