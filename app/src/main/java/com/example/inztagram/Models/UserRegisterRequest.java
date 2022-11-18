package com.example.inztagram.Models;

public class UserRegisterRequest {
    private String userName;
    private String fullName;
    private String emailId;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterRequest(String userName, String fullName, String emailId, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.emailId = emailId;
        this.password = password;
    }
}
