package es.zed.shared.exceptions.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * PokeApi exception type.
 */
@RequiredArgsConstructor
@Getter
public enum PokeApiExceptionType {

;
  /**
   * Code.
   */
  private final String code;
  /**
   * Http status.
   */
  private final HttpStatus httpStatus;
  /**
   * Message.
   */
  private final String message;

}
