package com.example.inztagram.viewModels;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.inztagram.Models.GetDetailsOfUserRequest;
import com.example.inztagram.Models.PostUploadResponse;
import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {
    public ProfileViewModel() {
        profileLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<UserRegisterRequest> profileLiveData;

    public MutableLiveData<UserRegisterRequest> getProfileLiveData() {
        return profileLiveData;
    }

    public void getUserDetails() {
        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        GetDetailsOfUserRequest getDetailsOfUserRequest = new GetDetailsOfUserRequest();
        String userId = LocalAuthService.getInstance().getSecretKey();
        String userName = LocalAuthService.getInstance().getUserName();
        if(userId == null || userName == null) {
            profileLiveData.postValue(null);
            return;
        }
        getDetailsOfUserRequest.setRequestingUserId(userId);
        getDetailsOfUserRequest.setUserNameToGetDetails(userName);
        Call<UserRegisterRequest> call = retrofitService.getDetailsOfUser(getDetailsOfUserRequest);
        call.enqueue(new Callback<UserRegisterRequest>() {
            @Override
            public void onResponse(Call<UserRegisterRequest> call, Response<UserRegisterRequest> response) {
                profileLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<UserRegisterRequest> call, Throwable t) {
                profileLiveData.postValue(null);
            }
        });
    }

    public void uploadProfilePhoto(byte[] imageFile) {
        String userId = LocalAuthService.getInstance().getSecretKey();
        String userName = LocalAuthService.getInstance().getUserName();
//        File imageFile = new File(uri.getPath());

        RequestBody requestBodyUserId = RequestBody.create(userId, MediaType.parse ("multipart/form-data"));
        RequestBody requestFile = RequestBody.create(imageFile, MediaType.parse ("multipart/form-data"));
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("imageFile", "imageFile", requestFile);

        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        Call<PostUploadResponse> call = retrofitService.uploadProfilePhoto(requestImage, requestBodyUserId);
        call.enqueue(new Callback<PostUploadResponse>() {
            @Override
            public void onResponse(Call<PostUploadResponse> call, Response<PostUploadResponse> response) {
                getUserDetails();
            }

            @Override
            public void onFailure(Call<PostUploadResponse> call, Throwable t) {
                getUserDetails();
            }
        });
    }
}
