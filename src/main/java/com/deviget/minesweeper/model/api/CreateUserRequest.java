package com.deviget.minesweeper.model.api;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    private static final String NAME_NOT_BLANK = "Name cannot be null or empty";
    private static final String LAST_NAME_NOT_BLANK = "LastName cannot be null or empty";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = LAST_NAME_NOT_BLANK)
    private String lastName;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
