package es.zed.shared.exceptions;

import es.zed.common.service.exception.PokemonException;
import es.zed.shared.exceptions.enums.PokeApiExceptionType;

/**
 * PokeApi exception.
 */
public class PokeApiException extends PokemonException {

  /**
   * Constructor.
   *
   * @param exception exception.
   */
  public PokeApiException(PokeApiExceptionType exception) {
    super(exception.getCode(), exception.getHttpStatus(), exception.getMessage(), null);
  }
}
