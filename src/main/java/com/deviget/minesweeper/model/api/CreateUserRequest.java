package com.deviget.minesweeper.model.api;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message = "Name cannot be null or empty")
    private String name;
    @NotBlank(message = "LastName cannot be null or empty")
    private String lastName;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }
}
