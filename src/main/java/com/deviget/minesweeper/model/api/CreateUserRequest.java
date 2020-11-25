package com.deviget.minesweeper.model.api;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    private static final String NAME_NOT_BLANK = "Name cannot be null or empty";
    private static final String LAST_NAME_NOT_BLANK = "LastName cannot be null or empty";
    private static final String USERNAME_NOT_BLANK = "UserName cannot be blank";

    @NotBlank(message = NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = LAST_NAME_NOT_BLANK)
    private String lastName;

    @NotBlank(message = USERNAME_NOT_BLANK)
    private String userName;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String lastName, String userName) {
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }
}
