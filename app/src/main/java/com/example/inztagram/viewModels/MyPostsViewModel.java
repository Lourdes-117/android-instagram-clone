package com.example.inztagram.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.inztagram.Models.GetUserPostsRequest;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostsViewModel extends ViewModel {
    public MyPostsViewModel() {
        myPostsLiveData = new MutableLiveData<>();
    }

    private MutableLiveData<List<PostModel>> myPostsLiveData;

    public MutableLiveData<List<PostModel>> getMyPostsLiveData() {
        return myPostsLiveData;
    }

    public void getMyPosts(int pagination) {
        RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);
        GetUserPostsRequest getUserPostsRequest = new GetUserPostsRequest();
        String userId = LocalAuthService.getInstance().getSecretKey();
        if(userId == null) {
            return;
        }
        getUserPostsRequest.setUserId(userId);
        getUserPostsRequest.setUserNameNeeded(LocalAuthService.getInstance().getUserName());
        getUserPostsRequest.setPagination(pagination);
        Call<List<PostModel>> call = retrofitService.getPostsOfUser(getUserPostsRequest);
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                myPostsLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                myPostsLiveData.postValue(null);
            }
        });
    }
}
