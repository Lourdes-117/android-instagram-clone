package com.example.inztagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inztagram.Models.PostModel;
import com.example.inztagram.R;
import com.example.inztagram.utility.EndpointBuilder;

import java.util.ArrayList;
import java.util.List;

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
        Glide.with(context)
                .asBitmap()
                .load(EndpointBuilder.getImageUrl(post.getFileId()))
                .centerCrop()
                .into(holder.postImage);
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

        public ViewHolder(@NonNull View itemView) {
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
    }
}
