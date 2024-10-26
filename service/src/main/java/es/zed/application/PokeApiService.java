package es.zed.application;

import es.zed.cache.CacheManagementService;
import es.zed.domain.intput.PokeApiInputPort;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.infrastructure.controller.KafkaController;
import es.zed.pokeapi.PokeCreatedEvent;
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
import org.springframework.cache.annotation.Cacheable;
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
   * Cache service.
   */
  private final CacheManagementService cacheService;

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @return the pokemon.
   */
  @Cacheable(value = Constants.POKE_CACHE, key = Constants.PK_NID_CACHE)
  @Override
  public PokemonAbilityResponseDto getPokemon(final String nid) {
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
    cacheService.clearCache(Constants.ALL_DB_POKEMON_CACHE);
    return pokemonResponseDto;
  }

  /**
   * Update pokemon.
   *
   * @param nid nid.
   * @param name name.
   */
  @Override
  public void updatePokemon(final String nid, final String name) {
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
    cacheService.clearCache(Constants.ALL_DB_POKEMON_CACHE);
  }

  /**
   * Get all pokemon.
   *
   * @param auth auth.
   * @return all pokemon.
   */
  @PreAuthorize(Constants.API_AUTHORITIES)
  @Cacheable(value = Constants.POKE_CACHE, key = Constants.ALL_DB_POKEMON_CACHE)
  @Override
  public PokemonResponseDto getAllPokemon(PokeAuthentication auth) {
    return pokeApiOutputPort.doCallInternalGetPokemon(pokeDbBaseUrl.concat(Constants.POKE_API_POKEMON),
        jwtService.createJwtFromSpec(auth.getJwtBearerToken()));
  }

}
