package es.zed.shared.utils;

/**
 * Contains all the constants in the project.
 */
public class Constants {

  /**
   * base path url.
   */
  public static final String BASE_URL = "/api";

  /**
   * pokemon url.
   */
  public static final String POKE_API_POKEMON = "/pokemon";

  /**
   * pokemon id filter url.
   */
  public static final String POKE_API_POKEMON_NID = POKE_API_POKEMON + "/{nid}";

  /**
   * pokemon id filter url.
   */
  public static final String POKE_API_POKEMON_ID_NAME = POKE_API_POKEMON_NID + "/{name}";

  /**
   * nid.
   */
  public static final String NID_URL_FILTER = "{nid}";

  /**
   * Api authorities.
   */
  public static final String API_AUTHORITIES = "hasAuthority('ADMIN')";

  /**
   * Response success.
   */
  public static final String RESPONSE_SUCCESS = "Success";

  /**
   * All db pokemon cache.
   */
  public static final String ALL_DB_POKEMON_CACHE = "'AllDbPokemon'";

  /**
   * Poke cache.
   */
  public static final String POKE_CACHE = "PokeCache";

  /**
   * PK nid caches.
   */
  public static final String PK_NID_CACHE = "'Pk' + #nid";

}
