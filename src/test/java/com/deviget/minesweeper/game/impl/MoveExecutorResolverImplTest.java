package com.deviget.minesweeper.game.impl;

import com.deviget.minesweeper.game.MoveExecutorResolver;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.model.MoveType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveExecutorResolverImplTest {

    @Test
    void whenCannotResolveAMoveExecutor_thenThrowsIllegalArgumentException() {
        //Given
        var resolver = new MoveExecutorResolverImpl(List.of());

        //When
        Executable executable = () -> resolver.resolveMoveExecutor(GameStatus.PLAYING, MoveType.FLIP);

        //Then
        assertThrows(IllegalArgumentException.class, executable,
                "Resolver is expected to fail because cannot resolve MoveExecutor for the given params");
    }

    @Test
    void whenResolveIsAbleToResolveAMoveExecutor_thenReturnsAMoveExecutor() {
        //Given
        var executor = new FlipCellMoveExecutor(null);
        var resolver = new MoveExecutorResolverImpl(List.of(executor));

        //When
        var result = resolver.resolveMoveExecutor(GameStatus.PLAYING, MoveType.FLIP);

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

    private List<Executable> resolveForEachGameStatusAndMoveType(MoveExecutorResolver resolver) {
        var executables = new ArrayList<Executable>();

        for (GameStatus gameStatus : GameStatus.values()) {
            for(MoveType moveType : MoveType.values()) {
                executables.add(() -> resolver.resolveMoveExecutor(gameStatus, moveType));
            }
        }

        return executables;
    }

    private MoveExecutorResolver createResolver() {
        var executors = List.of(
                new FlagCellMoveExecutor(null),
                new FlipCellMoveExecutor(null),
                new MarkCellMoveExecutor(null),
                new NoOpMoveExecutor(null),
                new RemoveFlagOrMarkMoveExecutor(null)
        );

        return new MoveExecutorResolverImpl(executors);
    }
}
