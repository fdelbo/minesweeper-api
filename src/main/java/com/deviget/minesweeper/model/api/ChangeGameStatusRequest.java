package com.deviget.minesweeper.model.api;

import javax.validation.constraints.NotBlank;

public class ChangeGameStatusRequest {

    private static final String USER_ID_BLANK = "UserId cannot be null or empty";

    @NotBlank(message = USER_ID_BLANK)
    private String userId;

    public ChangeGameStatusRequest() {
    }

    public ChangeGameStatusRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
