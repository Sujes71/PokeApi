package es.zed.application;

import es.zed.domain.intput.PokeApiInputPort;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.shared.mapper.event.PokeCreatedEventMapper;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * PokeApi service.
 */
@Service
@RequiredArgsConstructor
public class PokeApiService implements PokeApiInputPort {

  /**
   * PokeApi URL.
   */
  @Value("${poke-api.baseUrl}")
  private String pokeApiBaseUrl;

  /**
   * pokeApiUrlMapper.
   */
  private final CustomObjectMapper mapper;

  /**
   * pokeApiEndpoint.
   */
  private final PokeApiOutputPort pokeApiOutputPort;

  /**
   * Amqp controller.
   */
  private final AmqpController amqpController;

  /**
   * PokeApi event mapper.
   */
  private final PokeCreatedEventMapper eventMapper;

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @param auth auth.
   * @return the pokemon.
   */
  @Override
  public PokemonResponseDto getPokemon(final String nid, final String auth) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, nid);
    PokemonResponseDto pokemonResponseDto = pokeApiOutputPort.doCallGetPokemon(
        mapper.mapUrl(
            replacements,
            pokeApiBaseUrl.concat(Constants.POKE_API_POKEMON_NID)
        ), auth
    );
    amqpController.publish(eventMapper.buildEvent(pokemonResponseDto));
    return pokemonResponseDto;
  }
}
