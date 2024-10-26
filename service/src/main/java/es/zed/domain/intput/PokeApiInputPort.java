package es.zed.domain.intput;

import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.security.PokeAuthentication;

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
  PokemonAbilityResponseDto getPokemon(final String nid);

  /**
   * Update pokemon.
   *
   * @param nid nid.
   * @param name name.
   */
  void updatePokemon(final String nid, final String name);

  /**
   * Get pokemon.
   *
   * @param auth auth.
   * @return pokemonResponseDto.
   */
  PokemonResponseDto getAllPokemon(final PokeAuthentication auth);
}
