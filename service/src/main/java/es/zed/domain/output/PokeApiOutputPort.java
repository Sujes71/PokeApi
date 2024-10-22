package es.zed.domain.output;

import es.zed.dto.response.PokemonResponseDto;

/**
 * PokeApiOutputPort interface.
 */
public interface PokeApiOutputPort {

  /**
   * Call pokemon api.
   *
   * @param url url.
   * @param auth auth.
   * @return pokemonResponseDto.
   */
  PokemonResponseDto doCallGetPokemon(final String url, final String auth);

  /**
   * Call post pokeDb.
   *
   * @param url url.
   */
  void doCallPostAbility(final String url);

}
