package es.zed.infrastructure.api.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import es.zed.abstracts.AbstractEnpoint;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.utils.CustomObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * PokeApi endpoint component.
 */
@Component
public class PokeApiEndpoint extends AbstractEnpoint implements PokeApiOutputPort {

  /**
   * Constructor method.
   *
   * @param restTemplate restTemplate.
   * @param customObjectMapper mapper.
   */
  public PokeApiEndpoint(final RestTemplate restTemplate, final CustomObjectMapper customObjectMapper) {
    super(restTemplate, customObjectMapper);
  }

  /**
   * Method that get pokemon data from pokeApi.
   *
   * @param url url.
   * @return pokemonResponseDto.
   */
  @Override
  public PokemonAbilityResponseDto doCallGetPokemon(final String url) {
    return doCall(url, HttpMethod.GET, null, null, PokemonAbilityResponseDto.class);
  }

  /**
   * Method that get pokemon data from pokeDb.
   *
   * @param url url.
   * @param token token.
   * @return response.
   */
  @Override
  public PokemonResponseDto doCallInternalGetPokemon(final String url, final String token) {
    return doCallInternal(url, HttpMethod.GET, addDefaultHeaders(token), null, new TypeReference<>() {});
  }
}
