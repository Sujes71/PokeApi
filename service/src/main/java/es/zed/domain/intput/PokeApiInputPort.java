package es.zed.domain.intput;

import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.PokeAuthentication;
import org.springframework.http.ResponseEntity;

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
  ResponseEntity<ReqRespModel<PokemonAbilityResponseDto>> getPokemon(final String nid);

  /**
   * Update pokemon.
   *
   * @param nid nid.
   * @param name name.
   * @return void.
   */
  ResponseEntity<ReqRespModel<Void>> updatePokemon(final String nid, final String name);

  /**
   * Get pokemon.
   *
   * @param auth auth.
   * @return pokemonResponseDto.
   */
  ResponseEntity<ReqRespModel<PokemonResponseDto>> getAllPokemon(final PokeAuthentication auth);
}
