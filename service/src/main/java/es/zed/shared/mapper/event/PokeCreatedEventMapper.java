package es.zed.shared.mapper.event;

import es.zed.dto.response.PokemonResponseDto;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.pokeapi.PokeCreatedEventBody;
import es.zed.utils.EventMapper;
import es.zed.utils.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * poke api event mapper class.
 */
@RequiredArgsConstructor
@Component
public class PokeCreatedEventMapper implements EventMapper<PokemonResponseDto, PokeCreatedEvent> {

  /**
   * Poke created event.
   *
   * @param object build event.
   * @return event.
   */
  @Override
  public PokeCreatedEvent buildEvent(PokemonResponseDto object) {
    return PokeCreatedEvent.builder()
        .pokemonId(object.getId())
        .creationTs(System.currentTimeMillis())
        .typeId(PokeCreatedEvent.TYPE_ID)
        .messageId(UuidUtils.newUuid())
        .origin(PokeCreatedEvent.CONTEXT)
        .body(PokeCreatedEventBody.builder()
            .id(object.getId())
            .name(object.getName())
            .build())
        .build();
  }
}
