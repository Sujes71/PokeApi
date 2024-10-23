package es.zed.application;

import es.zed.domain.intput.PokeApiInputPort;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import es.zed.shared.mapper.event.PokeCreatedEventMapper;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
   * PokeDb URL.
   */
  @Value("${poke-db.baseUrl}")
  private String pokeDbBaseUrl;

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
   * Jwt service.
   */
  private final JwtService jwtService;

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @return the pokemon.
   */
  @Override
  public ResponseEntity<ReqRespModel<PokemonAbilityResponseDto>> getPokemon(final String nid) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, nid);

    PokemonAbilityResponseDto pokemonResponseDto = pokeApiOutputPort.doCallGetPokemon(
        mapper.mapUrl(
            replacements,
            pokeApiBaseUrl.concat(Constants.POKE_API_POKEMON_NID)
        )
    );
    amqpController.publish(eventMapper.buildEvent(pokemonResponseDto));

    return ResponseEntity.ok(new ReqRespModel<>(pokemonResponseDto, "Success"));
  }

  /**
   * Get all pokemon.
   *
   * @param auth auth.
   * @return all pokemon.
   */
  @Override
  public ResponseEntity<ReqRespModel<PokemonResponseDto>> getAllPokemon(PokeAuthentication auth) {
    PokemonResponseDto pokemonsResponseDto =
        pokeApiOutputPort.doCallInternalGetPokemon(pokeDbBaseUrl.concat(Constants.POKE_API_POKEMON),
            jwtService.createJwtFromSpec(auth.getJwtBearerToken()));
    return ResponseEntity.ok(new ReqRespModel<>(pokemonsResponseDto, "Success"));
  }

}
