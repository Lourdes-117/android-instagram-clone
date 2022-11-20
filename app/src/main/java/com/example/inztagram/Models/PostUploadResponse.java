package com.example.inztagram.Models;

public class PostUploadResponse {
    private String success;
    private String error;

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setError(String error) {
        this.error = error;
    }
}
