package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.*;

class DefaultAnnotationBasedValidatorTest {

    @Test
    void whenAllRequestFieldsAreInvalid_thenThrowsValidationExceptionWithViolationList() {
        //Given
        var validator = new DefaultAnnotationBasedValidator();
        var request = new FooRequest("  ", null);

        //When
        Executable executable = () -> validator.accept(request);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Request validation is expected to fail");

        assertEquals(2, exception.getViolations().size());
        assertTrue(exception.getViolations().contains(FooRequest.FIELD1_ERROR_MSG));
        assertTrue(exception.getViolations().contains(FooRequest.FIELD2_ERROR_MSG));
    }

    @Test
    void whenAllRequestFieldsAreValid_thenNoExceptionIsExpected() {
        //Given
        var validator = new DefaultAnnotationBasedValidator();
        var request = new FooRequest("field1", "field2");

        //When
        Executable executable = () -> validator.accept(request);

        //Then
        assertDoesNotThrow(executable,"Request validation is not expected to fail");
    }

    @Test
    void whenJustOneFieldIsInvalid_thenThrowsValidationExceptionWithViolationList() {
        //Given
        var validator = new DefaultAnnotationBasedValidator();
        var request = new FooRequest("  ", "field2");

        //When
        Executable executable = () -> validator.accept(request);

        //Then
        var exception = assertThrows(ValidationException.class, executable,
                "Request validation is expected to fail");

        assertEquals(1, exception.getViolations().size());
        assertTrue(exception.getViolations().contains(FooRequest.FIELD1_ERROR_MSG));
    }

    public class FooRequest {

        private static final String FIELD1_ERROR_MSG = "Field1 cannot be blank";
        private static final String FIELD2_ERROR_MSG = "Field2 cannot be null";

        @NotBlank(message = FIELD1_ERROR_MSG)
        private String field1;

        @NotNull(message = FIELD2_ERROR_MSG)
        private String field2;

        public FooRequest(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

}
