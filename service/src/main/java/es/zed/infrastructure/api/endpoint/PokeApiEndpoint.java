package es.zed.infrastructure.api.endpoint;

import es.zed.abstracts.AbstractEnpoint;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.enums.StatusType;
import es.zed.shared.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
   */
  public PokeApiEndpoint(final RestTemplate restTemplate) {
    super(restTemplate);
  }

  /**
   * Method that get pokemon data from pokeApi.
   *
   * @param url url.
   * @return pokemonResponseDto.
   */
  @Override
  public PokemonResponseDto doCallGetPokemon(final String url) {
    return doCall(url, HttpMethod.GET, null, null, PokemonResponseDto.class);
  }

  /**
   * Method that get pokemon data from pokeApi.
   *
   * @param url url.
   */
  @Override
  public void doCallPostAbility(final String url) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(Constants.STATUS_HEADER, StatusType.BACKING.name());
    doCall(url, HttpMethod.POST, httpHeaders, null, ResponseEntity.class);
  }
}
