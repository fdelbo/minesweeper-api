package com.deviget.minesweeper.model.api;

import javax.validation.constraints.NotBlank;

public class ChangeGameStatusRequest {

    private static final String USER_ID_BLANK = "UserId cannot be null or empty";
    private static final String STATUS_BLANK = "Status cannot be null or empty";

    @NotBlank(message = USER_ID_BLANK)
    private String userId;
    @NotBlank(message = STATUS_BLANK)
    private String status;

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }
}
