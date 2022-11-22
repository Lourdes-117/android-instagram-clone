package com.example.inztagram.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inztagram.Models.HomePostRequest;
import com.example.inztagram.Models.HomePostResponse;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    public HomeViewModel() {
        homePagePostsLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<List<PostModel>> homePagePostsLiveData;

    public MutableLiveData<List<PostModel>> getHomePagePostsLiveData() {
        return homePagePostsLiveData;
    }

    public void getPosts() {
        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        HomePostRequest homePostRequest = new HomePostRequest();
        String userId = LocalAuthService.getInstance().getSecretKey();
        if(userId == null) {
            return;
        }
        homePostRequest.setUserId(userId);
        Call<List<PostModel>> call = retrofitService.getHomePagePosts(homePostRequest);
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                homePagePostsLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                homePagePostsLiveData.postValue(null);
            }
        });
    }
}
