package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.model.document.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NoOpMoveExecutorTest {

    @Test
    void givenAGame_thenNoOpBehaviorIsExpected() {
        //Given
        var gameRepository = mock(GameRepository.class);
        var game = mock(Game.class);
        var executor = new NoOpMoveExecutor(gameRepository);

        //When
        executor.execute(game, 1, 1);

        //Then
        verifyNoInteractions(gameRepository);
        verifyNoInteractions(game);
    }
}
