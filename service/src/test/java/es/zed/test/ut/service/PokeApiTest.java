package es.zed.test.ut.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import es.zed.application.PokeApiService;
import es.zed.cache.CacheManagementService;
import es.zed.domain.output.PokeApiOutputPort;
import es.zed.dto.response.PokemonAbilityResponseDto;
import es.zed.dto.response.PokemonResponseDto;
import es.zed.infrastructure.controller.AmqpController;
import es.zed.infrastructure.controller.KafkaController;
import es.zed.pokeapi.PokeCreatedEvent;
import es.zed.pokeapi.PokeUpdatedEvent;
import es.zed.security.JwtBearerToken;
import es.zed.security.JwtService;
import es.zed.security.PokeAuthentication;
import es.zed.shared.mapper.event.PokeCreatedEventMapper;
import es.zed.shared.mapper.event.PokeUpdatedEventMapper;
import es.zed.shared.utils.Constants;
import es.zed.utils.CustomObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(MockitoExtension.class)
class PokeApiTest {

  @Mock
  private PokeApiOutputPort pokeApiOutputPort;

  @Mock
  private CustomObjectMapper mapper;

  @Mock
  private PokeCreatedEventMapper pokeCreatedEventMapper;

  @Mock
  private PokeUpdatedEventMapper pokeUpdatedEventMapper;

  @Mock
  private AmqpController amqpController;

  @Mock
  private CacheManagementService cacheService;

  @Mock
  private JwtService jwtService;

  @Mock
  private KafkaController kafkaController;

  @InjectMocks
  private PokeApiService pokemonService;

  private static final String NID = "123";
  private static final String CALL_URL = "https://pokeapi.co/api/v2/pokemon/{nid}";
  private static final String EXPECTED_URL = "https://pokeapi.co/api/v2/pokemon/" + NID;
  private PokemonAbilityResponseDto mockResponse;
  private PokeCreatedEvent mockPokeCreatedEvent;
  private PokeUpdatedEvent mockPokeUpdatedEvent;
  private PokemonResponseDto mockAllPokemonResponse;
  private JwtBearerToken jwtBearerToken;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(pokemonService, "pokeApiBaseUrl", "https://pokeapi.co/api/v2");
    ReflectionTestUtils.setField(pokemonService, "pokeDbBaseUrl", "http://localhost:8081/api");
    PodamFactory podamFactory = new PodamFactoryImpl();

    mockResponse = podamFactory.manufacturePojo(PokemonAbilityResponseDto.class);
    mockPokeCreatedEvent = podamFactory.manufacturePojo(PokeCreatedEvent.class);
    mockPokeUpdatedEvent = podamFactory.manufacturePojo(PokeUpdatedEvent.class);
    mockAllPokemonResponse = podamFactory.manufacturePojo(PokemonResponseDto.class);
    jwtBearerToken = podamFactory.manufacturePojo(JwtBearerToken.class);
  }

  @Test
  void testGetPokemon_Cacheable() {
    Map<String, String> replacements = Map.of("{nid}", NID);
    when(mapper.mapUrl(replacements, CALL_URL)).thenReturn(EXPECTED_URL);
    when(pokeApiOutputPort.doCallGetPokemon(EXPECTED_URL)).thenReturn(mockResponse);
    when(pokeCreatedEventMapper.buildEvent(mockResponse)).thenReturn(mockPokeCreatedEvent);

    PokemonAbilityResponseDto result = pokemonService.getPokemon(NID);

    assertEquals(mockResponse, result);
    verify(pokeApiOutputPort).doCallGetPokemon(EXPECTED_URL);
    verify(pokeCreatedEventMapper).buildEvent(mockResponse);
    verify(amqpController).publish(mockPokeCreatedEvent);
    verify(cacheService).clearCache(Constants.ALL_DB_POKEMON_CACHE);
  }

  @Test
  void testUpdatePokemon() {
    String name = "Pikachu";
    mockResponse.setName(name);

    when(mapper.mapUrl(Map.of(Constants.NID_URL_FILTER, NID), CALL_URL)).thenReturn(EXPECTED_URL);
    when(pokeApiOutputPort.doCallGetPokemon(EXPECTED_URL)).thenReturn(mockResponse);
    when(pokeUpdatedEventMapper.buildEvent(mockResponse)).thenReturn(mockPokeUpdatedEvent);

    pokemonService.updatePokemon(NID, name);

    verify(pokeApiOutputPort).doCallGetPokemon(EXPECTED_URL);
    verify(pokeUpdatedEventMapper).buildEvent(mockResponse);
    verify(kafkaController).publish(mockPokeUpdatedEvent);
    verify(cacheService).clearCache(Constants.ALL_DB_POKEMON_CACHE);
    assertEquals(name, mockResponse.getName());
  }

  @Test
  void testGetAllPokemon() {
    PokeAuthentication auth = new PokeAuthentication(jwtBearerToken);
    String token = "tokenTest";
    when(jwtService.createJwtFromSpec(auth.getJwtBearerToken())).thenReturn(token);
    when(pokeApiOutputPort.doCallInternalGetPokemon("http://localhost:8081/api" + Constants.POKE_API_POKEMON,
        token)).thenReturn(mockAllPokemonResponse);

    PokemonResponseDto result = pokemonService.getAllPokemon(auth);

    assertEquals(mockAllPokemonResponse, result);
    verify(pokeApiOutputPort).doCallInternalGetPokemon(
        "http://localhost:8081/api" + Constants.POKE_API_POKEMON,
        token
    );
  }
}