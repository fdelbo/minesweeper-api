package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.ActionExecutorResolver;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionExecutorResolverImplTest {

    @Test
    void whenCannotResolveAnActionExecutor_thenThrowsIllegalArgumentException() {
        //Given
        var resolver = new ActionExecutorResolverImpl(List.of());

        //When
        Executable executable = () -> resolver.resolveActionExecutor(GameStatus.PLAYING, MoveType.FLIP);

        //Then
        assertThrows(IllegalArgumentException.class, executable,
                "Resolver is expected to fail because cannot resolve ActionExecutor for the given params");
    }

    @Test
    void whenResolveIsAbleToResolveAnActionExecutor_thenReturnsAnActionExecutor() {
        //Given
        var executor = new FlipCellActionExecutor(null);
        var resolver = new ActionExecutorResolverImpl(List.of(executor));

        //When
        var result = resolver.resolveActionExecutor(GameStatus.PLAYING, MoveType.FLIP);

        //Then
        assertNotNull(result);
        assertEquals(executor, result);
    }

    @Test
    void givenAllCombinationsBetweenGameStatusAndMoveType_thenTheResolverMustBeAbleToResolveAllOfThem() {
        //Given
        var resolver = createResolver();

        //When
        var executables = resolveForEachGameStatusAndMoveType(resolver);

        //Then
        assertTrue(executables.size() > 0);
        assertAll("Resolvers are not expected to fail it must able to resolve any combination of " +
                "GameStatus and MoveType", executables);
    }

    private List<Executable> resolveForEachGameStatusAndMoveType(ActionExecutorResolver resolver) {
        var executables = new ArrayList<Executable>();

        for (GameStatus gameStatus : GameStatus.values()) {
            for(MoveType moveType : MoveType.values()) {
                executables.add(() -> resolver.resolveActionExecutor(gameStatus, moveType));
            }
        }

        return executables;
    }

    private ActionExecutorResolver createResolver() {
        var executors = List.of(
                new FlagCellActionExecutor(null),
                new FlipCellActionExecutor(null),
                new MarkCellActionExecutor(null),
                new NoOpActionExecutor(null),
                new RemoveFlagOrMarkActionExecutor(null)
        );

        return new ActionExecutorResolverImpl(executors);
    }
}
