package com.example.inztagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.inztagram.Models.PostModel;
import com.example.inztagram.R;
import com.example.inztagram.adapter.PostAdapter;
import com.example.inztagram.viewModels.HomeViewModel;
import com.example.inztagram.viewModels.MyPostsViewModel;

import java.util.List;

public class MyPostsFragment extends Fragment {
    private RecyclerView recyclerView;
    private MyPostsViewModel viewModel;
    private PostAdapter adapter;
    private View view;
    private LinearLayout noPostsFoundView;
    private boolean isLoading = false;
    private int numberOfPostsAvailable = 0;
    private int pagination = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setElements(View view) {
        this.view = view;
        recyclerView = view.findViewById(R.id.recycler_view);
        noPostsFoundView = view.findViewById(R.id.no_posts_found);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_posts, container, false);
        setElements(view);
        setupViewModel();
        setupRecyclerView();
        getPostsOfUser();
        setupRecyclerViewScrollObserver();
        return view;
    }

    private void setupViewModel() {
        this.viewModel = new ViewModelProvider(this).get(MyPostsViewModel.class);
        addViewModelObservers();
    }

    private void addViewModelObservers() {
        viewModel.getMyPostsLiveData().observe(getViewLifecycleOwner(), new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {
                isLoading = false;
                if(postModels.isEmpty()) {
                    if(pagination == 0) {
                        noPostsFoundView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.INVISIBLE);
                    }
                } else {
                    noPostsFoundView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    numberOfPostsAvailable += postModels.size();
                    adapter.addMorePosts(postModels);
                }
            }
        });
    }

    private void setupRecyclerView() {
        adapter = new PostAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void getPostsOfUser() {
        isLoading = true;
        viewModel.getMyPosts(pagination);
    }

    private void setupRecyclerViewScrollObserver() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == numberOfPostsAvailable - 1) {
                        //bottom of list!
                        getPostsOfUser();
                    }
                }
            }
        });
    }
}