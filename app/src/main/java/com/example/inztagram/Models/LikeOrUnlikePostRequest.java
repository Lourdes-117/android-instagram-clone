package com.example.inztagram.Models;

public class LikeOrUnlikePostRequest {
    String userId;
    String postId;

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
