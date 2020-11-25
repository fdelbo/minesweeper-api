package com.deviget.minesweeper.validator.impl;

import com.deviget.minesweeper.validator.EnumValue;
import org.apache.logging.log4j.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This validator validates that the conversion from String to some Enum is valid
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(Strings.isBlank(value)) {
            //Don't validate if blank
            return true;
        }

        try {
            Enum.valueOf((Class<? extends Enum>) enumClass, value);
            return true;
        } catch(IllegalArgumentException e) {
            return false;
        }
    }
}
