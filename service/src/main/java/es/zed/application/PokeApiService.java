package es.zed.application;

import es.zed.domain.intput.PokeApiInputPort;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.infrastructure.controller.KafkaController;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import es.zed.shared.mapper.event.PokeCreatedEventMapper;
import es.zed.shared.mapper.event.PokeUpdatedEventMapper;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  private final PokeCreatedEventMapper pokeCreatedEventMapper;

  /**
   * PokeApi event mapper.
   */
  private final PokeUpdatedEventMapper pokeUpdatedEventMapper;

  /**
   * Jwt service.
   */
  private final JwtService jwtService;

  /**
   * Kafka controller.
   */
  private final KafkaController kafkaController;

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
    PokeCreatedEvent event = pokeCreatedEventMapper.buildEvent(pokemonResponseDto);
    amqpController.publish(event);

    return ResponseEntity.ok(new ReqRespModel<>(pokemonResponseDto, Constants.RESPONSE_SUCCESS));
  }

  /**
   * Update pokemon.
   *
   * @param nid nid.
   * @param name name.
   * @return void.
   */
  @Override
  public ResponseEntity<ReqRespModel<Void>> updatePokemon(final String nid, final String name) {
    Map<String, String> replacements = new HashMap<>();
    replacements.put(Constants.NID_URL_FILTER, nid);

    PokemonAbilityResponseDto pokemonResponseDto = pokeApiOutputPort.doCallGetPokemon(
        mapper.mapUrl(
            replacements,
            pokeApiBaseUrl.concat(Constants.POKE_API_POKEMON_NID)
        )
    );
    pokemonResponseDto.setName(name);
    kafkaController.publish(pokeUpdatedEventMapper.buildEvent(pokemonResponseDto));

    return ResponseEntity.ok(new ReqRespModel<>(null, Constants.RESPONSE_SUCCESS));
  }

  /**
   * Get all pokemon.
   *
   * @param auth auth.
   * @return all pokemon.
   */
  @PreAuthorize(Constants.API_AUTHORITIES)
  @Override
  public ResponseEntity<ReqRespModel<PokemonResponseDto>> getAllPokemon(PokeAuthentication auth) {
    PokemonResponseDto pokemonsResponseDto =
        pokeApiOutputPort.doCallInternalGetPokemon(pokeDbBaseUrl.concat(Constants.POKE_API_POKEMON),
            jwtService.createJwtFromSpec(auth.getJwtBearerToken()));
    return ResponseEntity.ok(new ReqRespModel<>(pokemonsResponseDto, Constants.RESPONSE_SUCCESS));
  }

}
