package es.zed.domain.intput;

import es.zed.dto.response.PokemonResponseDto;
import es.zed.respmodel.ReqRespModel;
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
  ResponseEntity<ReqRespModel<PokemonResponseDto>> getPokemon(final String nid);
}
