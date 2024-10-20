package es.zed.application;

import es.zed.common.utils.CustomObjectMapper;
import es.zed.domain.intput.PokeApiHandlerPort;
import es.zed.event.pokedb.AbilityCreatedEvent;
import es.zed.infrastructure.api.endpoint.PokeApiEndpoint;
import es.zed.shared.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Poke api event handler.
 */
@Service
@RequiredArgsConstructor
public class PokeApiEventHandler implements PokeApiHandlerPort {

  /**
   * PokeDb path.
   */
  @Value("${poke-db.baseUrl}")
  private String pokeDbPath;

  /**
   * Mapper.
   */
  private final CustomObjectMapper mapper;

  /**
   * Poke api endpoint.
   */
  private final PokeApiEndpoint pokeApiEndpoint;

  @Override
  public void handleAbilityCreatedEvent(AbilityCreatedEvent event) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, event.getPokemonId());
    pokeApiEndpoint.doCallPostAbility(mapper.mapUrl(replacements, pokeDbPath.concat(Constants.POKE_DB_ABILITY_NID)));
  }
}
