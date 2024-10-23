package es.zed.domain.output;

import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;

/**
 * PokeApiOutputPort interface.
 */
public interface PokeApiOutputPort {

  /**
   * Call pokemon api.
   *
   * @param url url.
   * @return pokemonResponseDto.
   */
  PokemonAbilityResponseDto doCallGetPokemon(final String url);

  /**
   * Call pokemon api.
   *
   * @param url url.
   * @param token token.
   * @return response.
   */
  PokemonResponseDto doCallInternalGetPokemon(final String url, final String token);

}
