package com.deviget.minesweeper.controller;

import com.deviget.minesweeper.controller.exception.MainExceptionHandler;
import com.deviget.minesweeper.exception.ResourceNotFoundException;
import com.deviget.minesweeper.exception.ValidationException;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.api.GameResponse;
import com.deviget.minesweeper.service.GameService;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest {

    @Test
    void givenAValidRequest_whenCreateGame_thenHttpStatusOkAndResponseIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var converter = mock(Converter.class);
        var controller = new GameController(gameService, converter);
        var mockMvc = createMockMvc(controller);
        when(converter.convert(any())).thenReturn(createGameResponse());

        //When
        var resultActions = mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(CREATE_GAME_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("game-id"))
                .andExpect(jsonPath("$.last_resume_date").isNotEmpty())
                .andExpect(jsonPath("$.created_date").isNotEmpty())
                .andExpect(jsonPath("$.last_move_date").isNotEmpty())
                .andExpect(jsonPath("$.game_status").value("PLAYING"));
    }

    @Test
    void givenAnInvalidRequest_whenCreateGame_thenHttpStatusBadRequestIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var controller = new GameController(gameService, null);
        var mockMvc = createMockMvc(controller);
        when(gameService.create(any())).thenThrow(new ValidationException("Invalid request!"));

        //When
        var resultActions = mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(CREATE_GAME_JSON));

        //Then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
                .andExpect(jsonPath("$.message").value("Request has invalid parameters"))
                .andExpect(jsonPath("$.details[0]").value("Invalid request!"));
    }

    @Test
    void givenANonExistingUser_whenCreateGame_thenHttpStatusNotFoundIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var controller = new GameController(gameService, null);
        var mockMvc = createMockMvc(controller);
        when(gameService.create(any())).thenThrow(new ResourceNotFoundException("User not found!"));

        //When
        var resultActions = mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(CREATE_GAME_JSON));

        //Then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("The requested resource couldn't be found"))
                .andExpect(jsonPath("$.details[0]").value("User not found!"));
    }

    @Test
    void givenAServiceFail_whenCreateGame_thenHttpStatusServiceUnavailableIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var controller = new GameController(gameService, null);
        var mockMvc = createMockMvc(controller);
        when(gameService.create(any())).thenThrow(new RuntimeException("Error!"));

        //When
        var resultActions = mockMvc.perform(
                post("/games")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(CREATE_GAME_JSON));

        //Then
        resultActions.andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("UNKNOWN_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.details[0]").value("Error!"));
    }

    @Test
    void givenAServiceFail_whenMakeAMove_thenHttpStatusServiceUnavailableIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var controller = new GameController(gameService, null);
        var mockMvc = createMockMvc(controller);
        when(gameService.makeAMove(any(), any())).thenThrow(new RuntimeException("Error!"));

        //When
        var resultActions = mockMvc.perform(
                patch("/games/game-id-123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAKE_A_MOVE_REQUEST_JSON));

        //Then
        resultActions.andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.code").value("UNKNOWN_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.details[0]").value("Error!"));
    }

    @Test
    void givenANonExistingUser_whenMakeAMove_thenHttpStatusNotFoundIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var controller = new GameController(gameService, null);
        var mockMvc = createMockMvc(controller);
        when(gameService.makeAMove(any(), any())).thenThrow(new ResourceNotFoundException("User not found!"));

        //When
        var resultActions = mockMvc.perform(
                patch("/games/game-id-123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAKE_A_MOVE_REQUEST_JSON));

        //Then
        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("The requested resource couldn't be found"))
                .andExpect(jsonPath("$.details[0]").value("User not found!"));
    }

    @Test
    void givenAValidRequest_whenMakeAMove_thenHttpStatusOkAndResponseIsExpected() throws Exception {
        //Given
        var gameService = mock(GameService.class);
        var converter = mock(Converter.class);
        var controller = new GameController(gameService, converter);
        var mockMvc = createMockMvc(controller);
        when(converter.convert(any())).thenReturn(createGameResponse());

        //When
        var resultActions = mockMvc.perform(
                patch("/games/game-id-123")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAKE_A_MOVE_REQUEST_JSON));

        //Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("game-id"))
                .andExpect(jsonPath("$.last_resume_date").isNotEmpty())
                .andExpect(jsonPath("$.created_date").isNotEmpty())
                .andExpect(jsonPath("$.last_move_date").isNotEmpty())
                .andExpect(jsonPath("$.game_status").value("PLAYING"));
    }

    private MockMvc createMockMvc(GameController controller) {
        var jacksonBuilder = new Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .failOnUnknownProperties(false)
                .build();
        var converter = new MappingJackson2HttpMessageConverter(jacksonBuilder);

        return MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new MainExceptionHandler())
                .setMessageConverters(converter)
                .build();
    }

    private GameResponse createGameResponse() {
        var gameResponse = new GameResponse();
        gameResponse.setId("game-id");
        gameResponse.setLastResumeDate(Instant.now().toString());
        gameResponse.setCreatedDate(Instant.now().toString());
        gameResponse.setLastMoveDate(Instant.now().toString());
        gameResponse.setGameStatus(GameStatus.PLAYING.name());

        return gameResponse;
    }

    private static final String CREATE_GAME_JSON = "{\n" +
            "\t\"user_id\" : \"user-id-test\",\n" +
            "\t\"rows\" : 2,\n" +
            "\t\"columns\" : 2,\n" +
            "\t\"mines\" : 1\n" +
            "}";

    private static final String MAKE_A_MOVE_REQUEST_JSON = "{\n" +
            "\t\"user_id\" : \"user-id-test\",\n" +
            "\t\"row\" : 2,\n" +
            "\t\"column\" : 2,\n" +
            "\t\"type\" : \"FLAG\"\n" +
            "}";
}
