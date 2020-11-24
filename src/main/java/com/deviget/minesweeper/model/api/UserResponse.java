package com.deviget.minesweeper.model.api;

public class UserResponse {

    private String id;
    private String name;
    private String lastName;
    private String userName;

    public UserResponse() {
    }

    public UserResponse(String id, String name, String lastName, String userName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
