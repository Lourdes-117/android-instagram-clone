package com.example.inztagram.Models;

public class UserLoginResponse {
    String uuid;
    String error;

    public String getUuid() {
        return uuid;
    }

    public String getError() {
        return error;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setError(String error) {
        this.error = error;
    }
}
