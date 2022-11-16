package com.example.inztagram.viewModels;

import androidx.annotation.Nullable;

import com.example.inztagram.Models.RegisterModel;

public class RegisterViewModel {
    private Integer userNameMinLength = 3;
    private Integer passwordMinLength = 4;
    private String emailIDRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    @Nullable
    public String getRegisterErrorsIfAvailable(RegisterModel registerModel) {
        if (checkForErrorsInUserName(registerModel.getUserName()) != null) {
            return checkForErrorsInUserName(registerModel.getUserName());
        }
        if (checkForErrorsInName(registerModel.getFullName()) != null) {
            return checkForErrorsInName(registerModel.getFullName());
        }
        if (checkForErrorsInEmailId(registerModel.getEmailId()) != null) {
            return checkForErrorsInEmailId(registerModel.getEmailId());
        }
        if (checkForErrorsInPassword(registerModel.getPassword()) != null) {
            return checkForErrorsInPassword(registerModel.getPassword());
        }
        return null;
    }

    @Nullable
    private String checkForErrorsInUserName(String userName) {
        if(userName.isEmpty()) {
            return "Username Can't be empty";
        }
        if(userName.length() < userNameMinLength) {
            return "Username too short";
        }
        return null;
    }

    @Nullable
    private String checkForErrorsInPassword(String password) {
        if(password.isEmpty()) {
            return "Password Can't be empty";
        }
        if(password.length() < passwordMinLength) {
            return "Password too short";
        }
        return null;
    }

    @Nullable
    private String checkForErrorsInName(String password) {
        if(password.isEmpty()) {
            return "Name Can't be empty";
        }
        return null;
    }

    @Nullable
    private String checkForErrorsInEmailId(String emailId) {
        if(emailId.isEmpty()) {
            return "Please Enter email ID";
        }
        if(!emailId.matches(emailIDRegex)) {
            return "Please Enter Valid Email ID";
        }
        return null;
    }
}
