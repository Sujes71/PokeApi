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
   * @return pokemonResponseDto.
   */
  PokemonResponseDto getPokemon(final String nid);
}
