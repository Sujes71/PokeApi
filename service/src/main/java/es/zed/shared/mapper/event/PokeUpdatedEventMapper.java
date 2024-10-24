package es.zed.shared.mapper.event;

import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.pokeapi.PokeUpdatedEvent;
import es.zed.pokeapi.PokeUpdatedEventBody;
import es.zed.utils.EventMapper;
import es.zed.utils.UuidUtils;
import org.springframework.stereotype.Component;

@Component
public class PokeUpdatedEventMapper implements EventMapper<PokemonAbilityResponseDto, PokeUpdatedEvent> {

  @Override
  public PokeUpdatedEvent buildEvent(PokemonAbilityResponseDto object) {
    return PokeUpdatedEvent.builder()
        .pokemonId(object.getId())
        .creationTs(System.currentTimeMillis())
        .typeId(PokeUpdatedEvent.TYPE_ID)
        .messageId(UuidUtils.newUuid())
        .origin(PokeUpdatedEvent.CONTEXT)
        .body(PokeUpdatedEventBody.builder()
            .id(object.getId())
            .name(object.getName())
            .build())
        .build();
  }
}
