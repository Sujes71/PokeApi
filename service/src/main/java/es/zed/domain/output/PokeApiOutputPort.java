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
   * @return pokemonResponseDto.
   */
  PokemonResponseDto doCallGetPokemon(final String url);

  /**
   * Call post pokeDb.
   *
   * @param url url.
   */
  public void doCallPostAbility(final String url);

}
