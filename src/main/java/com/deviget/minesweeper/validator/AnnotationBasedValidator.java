package com.deviget.minesweeper.validator;

import java.util.function.Consumer;

/**
 * This validator validates the annotated fields of a given object.
 * The annotations used with the fields must be part of javax.validations
 */
@FunctionalInterface
public interface AnnotationBasedValidator extends Consumer<Object> {
}
