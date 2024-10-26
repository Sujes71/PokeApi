package es.zed.infrastructure.controller;

import es.zed.domain.intput.PokeApiInputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.respmodel.ReqRespModel;
import es.zed.security.PokeAuthentication;
import es.zed.shared.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_API_POKEMON_NID, produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ReqRespModel<PokemonAbilityResponseDto>> getPokemon(@PathVariable final String nid) {
    return ResponseEntity.ok(new ReqRespModel<>(pokeApiInputPort.getPokemon(nid), Constants.RESPONSE_SUCCESS));
  }

  /**
   * Method to get the pokemon by id.
   *
   * @param nid nid.
   * @param name name.
   * @return the pokemon.
   */
  @PutMapping(path = Constants.POKE_API_POKEMON_ID_NAME, produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ReqRespModel<Void>> updatePokemon(@PathVariable final String nid, @PathVariable final String name) {
    pokeApiInputPort.updatePokemon(nid, name);
    return ResponseEntity.ok(new ReqRespModel<>(null, "Success"));
  }

  /**
   * Method to get the pokemon by id.
   *
   * @param auth auth.
   * @return the pokemon.
   */
  @GetMapping(path = Constants.POKE_API_POKEMON, produces = MediaType.APPLICATION_JSON_VALUE)
  private ResponseEntity<ReqRespModel<PokemonResponseDto>> getAllPokemon(final PokeAuthentication auth) {
    return ResponseEntity.ok(new ReqRespModel<>(pokeApiInputPort.getAllPokemon(auth), Constants.RESPONSE_SUCCESS));
  }
}
