package com.example.inztagram.apiService;

import com.example.inztagram.Models.UserLoginRequest;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.Models.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetroService {

    @POST("userRegister")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UserRegisterResponse> createUser(@Body UserRegisterRequest userRegisterRequest);

    @POST("login")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UserLoginResponse> loginUser(@Body UserLoginRequest userLoginRequest);
}