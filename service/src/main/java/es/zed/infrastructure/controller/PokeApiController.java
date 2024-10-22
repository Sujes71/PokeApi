package es.zed.infrastructure.controller;

import es.zed.domain.intput.PokeApiInputPort;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PokeApi controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.BASE_URL)
public class PokeApiController {

  /**
   * PokeApi service.
   */
  private final PokeApiInputPort pokeApiInputPort;

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @param auth auth.
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_API_POKEMON_NID, produces = MediaType.APPLICATION_JSON_VALUE)
  private PokemonResponseDto getPokemon(@PathVariable final String nid,
      @RequestHeader(name = "Authorization") final String auth) {
    return pokeApiInputPort.getPokemon(nid, auth);
  }
}
