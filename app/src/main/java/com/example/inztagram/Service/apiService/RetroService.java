package com.example.inztagram.Service.apiService;

import com.example.inztagram.Models.GetDetailsOfUserRequest;
import com.example.inztagram.Models.GetUserPostsRequest;
import com.example.inztagram.Models.HomePostRequest;
import com.example.inztagram.Models.HomePostResponse;
import com.example.inztagram.Models.LikeOrUnlikePostRequest;
import com.example.inztagram.Models.LikeOrUnlikePostResponse;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.Models.PostUploadResponse;
import com.example.inztagram.Models.UserLoginRequest;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.Models.UserRegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetroService {

    @POST("userRegister")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UserRegisterResponse> createUser(@Body UserRegisterRequest userRegisterRequest);

    @POST("login")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest userLoginRequest);

    @Multipart
    @POST("upload-post")
    Call<PostUploadResponse> uploadImagePost(@Part MultipartBody.Part image,
                                             @Part("userId")RequestBody userId,
                                             @Part("imageCaption")RequestBody imageCaption);

    @POST("get-posts")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<PostModel>> getHomePagePosts(@Body HomePostRequest homePostRequest);

    @POST("get-posts-user")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<List<PostModel>> getPostsOfUser(@Body GetUserPostsRequest getUserPostsRequest);

    @POST("getDetailsOfUser")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UserRegisterRequest> getDetailsOfUser(@Body GetDetailsOfUserRequest getDetailsOfUserRequest);

    @Multipart
    @POST("upload-profile-photo")
    Call<PostUploadResponse> uploadProfilePhoto(@Part MultipartBody.Part imageFile,
                                             @Part("userId")RequestBody userId);
    @POST("like-or-unlike-post")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<LikeOrUnlikePostResponse> likeOrUnlikePost(@Body LikeOrUnlikePostRequest likeOrUnlikePostRequest);
}