package com.example.inztagram.Models;

public class GetDetailsOfUserRequest {
    String requestingUserId;
    String userNameToGetDetails;

    public String getRequestingUserId() {
        return requestingUserId;
    }

    public String getUserNameToGetDetails() {
        return userNameToGetDetails;
    }

    public void setRequestingUserId(String requestingUserId) {
        this.requestingUserId = requestingUserId;
    }

    public void setUserNameToGetDetails(String userNameToGetDetails) {
        this.userNameToGetDetails = userNameToGetDetails;
    }
}
