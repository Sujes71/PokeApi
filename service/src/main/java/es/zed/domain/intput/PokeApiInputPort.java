package es.zed.domain.intput;

import es.zed.dto.response.PokemonResponseDto;

/**
 * PokeApiInputPort interface.
 */
public interface PokeApiInputPort {

  /**
   * Get pokemon.
   *
   * @param nid nid.
   * @param auth auth.
   * @return pokemonResponseDto.
   */
  PokemonResponseDto getPokemon(final String nid, final String auth);
}
