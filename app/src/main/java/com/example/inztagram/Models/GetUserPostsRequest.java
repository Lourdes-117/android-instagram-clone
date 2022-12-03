package com.example.inztagram.Models;

public class GetUserPostsRequest {
    String userId;
    String userNameNeeded;
    Integer pagination;

    public String getUserId() {
        return userId;
    }

    public String getUserNameNeeded() {
        return userNameNeeded;
    }

    public Integer getPagination() {
        return pagination;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserNameNeeded(String userNameNeeded) {
        this.userNameNeeded = userNameNeeded;
    }

    public void setPagination(Integer pagination) {
        this.pagination = pagination;
    }
}
