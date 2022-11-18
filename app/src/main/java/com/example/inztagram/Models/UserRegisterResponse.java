package com.example.inztagram.Models;

public class UserRegisterResponse {
    String success;
    String error;

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
