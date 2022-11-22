package com.example.inztagram.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inztagram.Models.HomePostResponse;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.R;
import com.example.inztagram.adapter.PostAdapter;
import com.example.inztagram.viewModels.HomeViewModel;
import com.example.inztagram.viewModels.PostImageViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    private PostAdapter adapter;
    HomeViewModel viewModel;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setElements(view);
        initViewModel();
        getPosts();
        return view;
    }

    private void setElements(View view) {
        this.view = view;
        this.recyclerView = view.findViewById(R.id.recyclerView_posts);
        adapter = new PostAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initViewModel() {
        this.viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        this.addObservers();
    }

    private void addObservers() {
        viewModel.getHomePagePostsLiveData().observe(getViewLifecycleOwner(), new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                if(postModels != null) {
                    adapter.addMorePosts(postModels);
                    return;
                }
            }
        });
    }

    private void getPosts() {
        viewModel.getPosts();
    }
}