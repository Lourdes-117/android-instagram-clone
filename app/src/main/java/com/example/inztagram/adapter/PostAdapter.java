package com.example.inztagram.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inztagram.Models.LikeOrUnlikePostRequest;
import com.example.inztagram.Models.LikeOrUnlikePostResponse;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.R;
import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;
import com.example.inztagram.utility.EndpointBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<PostModel> posts = new ArrayList<>();
    private final int VIEW_TYPE_POST = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public PostAdapter(Context context) {
        this.context = context;
        this.posts = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_POST) {
            View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        try {
            return posts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_POST;
        } catch (Exception exception) {
            return VIEW_TYPE_LOADING;
        }

    }

    public void addMorePosts(List<PostModel> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            this.populatePostWithValue((PostViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    private void populatePostWithValue(PostViewHolder holder, int position) {
        PostModel post = posts.get(position);

        holder.postId = post.getFileId();
        setProfilePhoto(holder, post.getUserName());
        holder.setLikedOrUnlikedIcon(post.isPostLiked());
        holder.setNumberOfLikes(post.getLikes().size());
        setOnClickListenerForLikes(holder);

        Glide.with(context)
                .asBitmap()
                .load(EndpointBuilder.getImageUrl(post.getFileId()))
                .centerCrop()
                .into(holder.postImage);
        holder.userName.setText(post.getUserName());
        holder.numberOfLikes.setText(post.getLikes().size() + "Likes");
        holder.imageCaption.setText(post.getImageCaption());
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void setProfilePhoto(PostViewHolder holder, String userName) {
        Drawable drawable = holder.profileImage.getDrawable();
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(EndpointBuilder.getProfileImageUrlForUserName(userName))
                .error(drawable)
                .into(holder.profileImage);
    }

    private void setOnClickListenerForLikes(PostViewHolder holder) {
        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);

                LikeOrUnlikePostRequest likeOrUnlikePostRequest = new LikeOrUnlikePostRequest();
                likeOrUnlikePostRequest.setUserId(LocalAuthService.getInstance().getSecretKey());
                likeOrUnlikePostRequest.setPostId(holder.postId);

                PostModel postSelected = null;
                for (int i = 0; i < posts.size(); i++) {
                    PostModel post = posts.get(i);
                    if(post.getFileId() == holder.postId) {
                        postSelected = post;
                    }
                }

                if(postSelected == null) { return; }

                if(holder.isLiked) {
                    postSelected.getLikes().remove(LocalAuthService.getInstance().getUserName());
                    holder.isLiked = !holder.isLiked;
                    if(holder.numberOfLikesForPostInt != null) {
                        holder.setNumberOfLikes(holder.numberOfLikesForPostInt-1);
                        holder.setLikedOrUnlikedIcon(holder.isLiked);
                    }
                } else {
                    postSelected.getLikes().add(LocalAuthService.getInstance().getUserName());
                    holder.isLiked = !holder.isLiked;
                    holder.setNumberOfLikes(holder.numberOfLikesForPostInt+1);
                    holder.setLikedOrUnlikedIcon(holder.isLiked);
                }

                Call<LikeOrUnlikePostResponse> call = retrofitService.likeOrUnlikePost(likeOrUnlikePostRequest);
                call.enqueue(new Callback<LikeOrUnlikePostResponse>() {
                    @Override
                    public void onResponse(Call<LikeOrUnlikePostResponse> call, Response<LikeOrUnlikePostResponse> response) {
                        return;
                    }

                    @Override
                    public void onFailure(Call<LikeOrUnlikePostResponse> call, Throwable t) {
                        return;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size() + 1;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;
        public ImageView postImage;
        public ImageView likeImage;
        public ImageView comment;
        public ImageView save;
        public ImageView more;

        public TextView userName;
        public TextView numberOfLikes;
        public TextView imageCaption;

        private String likesString = "Likes";
        private Boolean isLiked = false;
        private Integer numberOfLikesForPostInt = 0;
        private String postId;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            setElements();
        }

        public void setElements() {
            profileImage = itemView.findViewById(R.id.profile_image);
            postImage = itemView.findViewById(R.id.post_image);
            likeImage = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            more = itemView.findViewById(R.id.more);

            userName = itemView.findViewById(R.id.textView_userName);
            numberOfLikes = itemView.findViewById(R.id.number_of_likes);
            imageCaption = itemView.findViewById(R.id.post_caption);
        }

        public void setLikedOrUnlikedIcon(Boolean isLiked) {
            this.isLiked = isLiked;
            likeImage.setImageResource( isLiked ? R.drawable.ic_like_liked : R.drawable.ic_like_not_liked);
        }

        public void setNumberOfLikes(Integer numberOfLikesForPost) {
            if(numberOfLikesForPost == null || numberOfLikesForPost < 0 ) {
                numberOfLikesForPost = 0;
            }
            numberOfLikesForPostInt = numberOfLikesForPost;
            numberOfLikes.setText(numberOfLikesForPost + likesString);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
