package com.example.inztagram.Models;

public class ProfilePhotoUploadResponse {
    String error;
    String success;

    public String getError() {
        return error;
    }

    public String getSuccess() {
        return success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
