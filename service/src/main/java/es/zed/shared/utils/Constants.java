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
   * pokemon ability filter url.
   */
  public static final String POKE_DB_ABILITY = "/ability";

  /**
   * pokemon id filter url.
   */
  public static final String POKE_DB_ABILITY_NID = POKE_DB_ABILITY + "/{nid}";

  /**
   * nid.
   */
  public static final String NID_URL_FILTER = "{nid}";

  /**
   * nid.
   */
  public static final String STATUS_HEADER = "x-status";
}
