package com.example.inztagram.Models;

import com.example.inztagram.Service.LocalAuthService;

import java.util.ArrayList;

public class PostModel {
    String fileId;
    String userId;
    String userName;
    String imageCaption;
    ArrayList<String> likes;

    public String getFileId() {
        return fileId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public Boolean isPostLiked() {
        String userName = LocalAuthService.getInstance().getUserName();
        if(likes == null || userName == null) {
            return false;
        }
        return likes.contains(userName);
    }
}
