package com.example.inztagram.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inztagram.Models.UserLoginRequest;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    public LoginViewModel() {
        userLoginResponseMutableLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<UserLoginResponse> userLoginResponseMutableLiveData;

    public MutableLiveData<UserLoginResponse> getCreateUserAndLoginObserver() {
        return userLoginResponseMutableLiveData;
    }

    public void loginUser(UserLoginRequest userLoginRequest) {
        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        Call<UserLoginResponse> call = retrofitService.loginUser(userLoginRequest);
        call.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                userLoginResponseMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                userLoginResponseMutableLiveData.postValue(null);
            }
        });
    }
}
