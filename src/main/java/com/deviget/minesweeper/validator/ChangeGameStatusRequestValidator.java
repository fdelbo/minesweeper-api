package com.deviget.minesweeper.validator;

import com.deviget.minesweeper.model.api.ChangeGameStatusRequest;

import java.util.function.Consumer;

@FunctionalInterface
public interface ChangeGameStatusRequestValidator extends Consumer<ChangeGameStatusRequest> {
}
