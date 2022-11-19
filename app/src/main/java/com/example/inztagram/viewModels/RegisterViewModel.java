package com.example.inztagram.viewModels;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inztagram.Models.UserLoginRequest;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.Models.UserRegisterResponse;
import com.example.inztagram.apiService.RetroService;
import com.example.inztagram.apiService.RetrofitService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    public RegisterViewModel() {
        userLoginResponseMutableLiveData = new MutableLiveData<>();
    }

    private Integer userNameMinLength = 3;
    private Integer passwordMinLength = 4;
    private String emailIDRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    private MutableLiveData<UserLoginResponse> userLoginResponseMutableLiveData;

    public MutableLiveData<UserLoginResponse> getCreateUserAndLoginObserver() {
        return userLoginResponseMutableLiveData;
    }

    @Nullable
    public String getRegisterErrorsIfAvailable(UserRegisterRequest userRegisterRequest) {
        if (checkForErrorsInUserName(userRegisterRequest.getUserName()) != null) {
            return checkForErrorsInUserName(userRegisterRequest.getUserName());
        }
        if (checkForErrorsInName(userRegisterRequest.getFullName()) != null) {
            return checkForErrorsInName(userRegisterRequest.getFullName());
        }
        if (checkForErrorsInEmailId(userRegisterRequest.getEmailId()) != null) {
            return checkForErrorsInEmailId(userRegisterRequest.getEmailId());
        }
        if (checkForErrorsInPassword(userRegisterRequest.getPassword()) != null) {
            return checkForErrorsInPassword(userRegisterRequest.getPassword());
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

    public void createNewUser(UserRegisterRequest userRegisterRequest) {
        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        Call<UserRegisterResponse> call = retrofitService.createUser(userRegisterRequest);
        call.enqueue(new Callback<UserRegisterResponse>() {
            @Override
            public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getError() != null) {
                        // Registration Failed
                        UserLoginResponse loginResponse = new UserLoginResponse();
                        loginResponse.setError(response.body().getError());
                        userLoginResponseMutableLiveData.postValue(loginResponse);
                    } else {
                        // Registration Successful -> Login
                        UserLoginRequest userLoginRequest = new UserLoginRequest();
                        userLoginRequest.setUserName(userLoginRequest.getUserName());
                        userLoginRequest.setPassword(userLoginRequest.getPassword());
                        loginUser(userLoginRequest);
                    }
                } else {
                    userLoginResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                userLoginResponseMutableLiveData.postValue(null);
            }
        });
    }

    private void loginUser(UserLoginRequest userLoginRequest) {
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
