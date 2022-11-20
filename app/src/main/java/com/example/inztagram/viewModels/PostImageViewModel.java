package com.example.inztagram.viewModels;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.inztagram.Models.PostUploadResponse;
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

public class PostImageViewModel extends ViewModel {
    public PostImageViewModel() {
        postUploadResponseMutableLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<PostUploadResponse> postUploadResponseMutableLiveData;

    public MutableLiveData<PostUploadResponse> getPostUploadResponseMutableLiveData() {
        return postUploadResponseMutableLiveData;
    }

    public void postImage(Uri uri, String imageCaption) {
        String userId = LocalAuthService.getInstance().getSecretKey();
        if(userId == null || uri == null) {
            PostUploadResponse postUploadResponse = new PostUploadResponse();
            postUploadResponse.setError("Please Select an Image");
            postUploadResponseMutableLiveData.postValue(postUploadResponse);
            return;
        }
        File imageFile = new File(uri.getPath());
        if(imageCaption == null) {
            imageCaption = "";
        }
        RequestBody requestBodyUserId = RequestBody.create(userId, MediaType.parse ("multipart/form-data"));
        RequestBody requestBodyCaption = RequestBody.create(imageCaption, MediaType.parse ("multipart/form-data"));
        RequestBody requestFile = RequestBody.create(imageFile, MediaType.parse ("multipart/form-data"));
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("imageFile", "imageFile", requestFile);

        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        Call<PostUploadResponse> call = retrofitService.uploadImagePost(requestImage, requestBodyUserId, requestBodyCaption);
        call.enqueue(new Callback<PostUploadResponse>() {
            @Override
            public void onResponse(Call<PostUploadResponse> call, Response<PostUploadResponse> response) {
                if(response.body() == null) {
                    postUploadResponseMutableLiveData.postValue(null);
                } else {
                    postUploadResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostUploadResponse> call, Throwable t) {
                postUploadResponseMutableLiveData.postValue(null);
            }
        });
    }
}
