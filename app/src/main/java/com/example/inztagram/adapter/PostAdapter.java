package com.example.inztagram.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inztagram.Models.LikeOrUnlikePostRequest;
import com.example.inztagram.Models.LikeOrUnlikePostResponse;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.R;
import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetroService;
import com.example.inztagram.Service.apiService.RetrofitService;
import com.example.inztagram.utility.EndpointBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<PostModel> posts;

    public PostAdapter(Context context) {
        this.context = context;
        this.posts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new PostAdapter.ViewHolder(view);
    }


    public void addMorePosts(List<PostModel> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel post = posts.get(position);

        holder.postId = post.getFileId();
        setProfilePhoto(holder, post.getUserName());
        holder.setLikedOrUnlikedIcon(post.isPostLiked());
        holder.setNumberOfLikes(post.getLikes().size());

        Glide.with(context)
                .asBitmap()
                .load(EndpointBuilder.getImageUrl(post.getFileId()))
                .centerCrop()
                .into(holder.postImage);
        holder.userName.setText(post.getUserName());
        holder.numberOfLikes.setText(post.getLikes().size() + "Likes");
        holder.imageCaption.setText(post.getImageCaption());
    }

    private void setProfilePhoto(ViewHolder holder, String userName) {
        Drawable drawable = holder.profileImage.getDrawable();
        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load(EndpointBuilder.getProfileImageUrlForUserName(userName))
                .error(drawable)
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setElements();
            setOnClickListeners();
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

        private void setOnClickListeners() {
            setOnClickListenerForLikes();
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

        private void setOnClickListenerForLikes() {
            likeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RetroService retrofitService = RetrofitService.getRetrofitInstance().create(RetroService.class);

                    LikeOrUnlikePostRequest likeOrUnlikePostRequest = new LikeOrUnlikePostRequest();
                    likeOrUnlikePostRequest.setUserId(LocalAuthService.getInstance().getSecretKey());
                    likeOrUnlikePostRequest.setPostId(postId);

                    if(isLiked) {
                        isLiked = !isLiked;
                        if(numberOfLikesForPostInt != null) {
                            setNumberOfLikes(numberOfLikesForPostInt-1);
                            setLikedOrUnlikedIcon(isLiked);
                        }
                    } else {
                        isLiked = !isLiked;
                        setNumberOfLikes(numberOfLikesForPostInt+1);
                        setLikedOrUnlikedIcon(isLiked);
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
    }
}
