package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.validator.EnumValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnumValueValidatorTest {

    @Test
    void whenTheValueIsNotAValidEnum_thenReturnFalse() {
        //Given
        var invalidEnum = "NOT_VALID_ENUM";
        var validator = new EnumValueValidator();
        var annotation = mock(EnumValue.class);
        when(annotation.enumClass()).then(i -> FooEnum.class);
        validator.initialize(annotation);

        //When
        var isValid = validator.isValid(invalidEnum, null);

        //Then
        assertFalse(isValid);
    }

    @Test
    void whenTheValueIsAValidEnum_thenReturnTrue() {
        //Given
        var validEnum = "FOO";
        var validator = new EnumValueValidator();
        var annotation = mock(EnumValue.class);
        when(annotation.enumClass()).then(i -> FooEnum.class);
        validator.initialize(annotation);

        //When
        var isValid = validator.isValid(validEnum, null);

        //Then
        assertTrue(isValid);
    }

    @Test
    void whenTheValueIsABlankString_thenReturnTrue() {
        //Given
        var blankString = "  ";
        var validator = new EnumValueValidator();
        var annotation = mock(EnumValue.class);
        when(annotation.enumClass()).then(i -> FooEnum.class);
        validator.initialize(annotation);

        //When
        var isValid = validator.isValid(blankString, null);

        //Then
        assertTrue(isValid);
    }

    public enum FooEnum {
        FOO,
        BAR,
        ;
    }
}
