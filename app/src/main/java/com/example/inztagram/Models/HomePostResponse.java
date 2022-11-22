package com.example.inztagram.Models;

import java.util.ArrayList;
import java.util.List;

public class HomePostResponse {
    ArrayList<PostModel> posts;

    public ArrayList<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    public void addMorePosts(List<PostModel> posts) {
        this.posts.addAll(posts);
    }
}
