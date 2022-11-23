package com.example.inztagram.controllers;

import com.example.inztagram.Models.PostUploadResponse;
import com.example.inztagram.R;
import com.example.inztagram.utility.InztaAppCompatActivity;
import com.example.inztagram.viewModels.PostImageViewModel;
import com.yalantis.ucrop.UCrop;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;
import java.util.UUID;

public class PostImageActivity extends InztaAppCompatActivity {
    private Button buttonPost;
    private ImageView imageViewImageToPost;
    private EditText editTextDescription;
    private ImageView imageViewClose;
    private ActivityResultLauncher<String>  mGetContent;
    private LinearLayout parent;

    private PostImageViewModel viewModel;

    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

        setElements();
        setInitialView();
        initViewModel();
        setOnClickListeners();
        showImagePicker();
   }

    private void setElements() {
        buttonPost = findViewById(R.id.button_post);
        imageViewImageToPost = findViewById(R.id.imageView_toPost);
        imageViewClose = findViewById(R.id.imageView_close);
        editTextDescription = findViewById(R.id.editText_description);
        parent = findViewById(R.id.parent);
    }

    private void setInitialView() {
        buttonPost.setEnabled(false);
        buttonPost.setTextColor(Color.GRAY);
    }

    private void setOnClickListeners() {
        handleCloseButton();
        handleOnTapImageToPickImage();
        handleImageSelected();
        handleOnTapPost();
    }

    private void initViewModel() {
        this.viewModel = new ViewModelProvider(this).get(PostImageViewModel.class);
        this.addObservers();
    }

    private void handleImageSelected() {
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                String destinationUri = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();
                UCrop.Options options = new UCrop.Options();
                options.setBrightnessEnabled(true);
                options.setContrastEnabled(true);
                options.setSharpnessEnabled(true);
                options.setSaturationEnabled(true);
                UCrop.of(result, Uri.fromFile(new File(getCacheDir(), destinationUri)))
                        .withOptions(options)
                        .withAspectRatio(0,0)
                        .withMaxResultSize(2000, 2000)
                        .start(PostImageActivity.this);
            }
        });
    }

    private void showImagePicker() {
        mGetContent.launch("image/*");
    }

    private void handleOnTapImageToPickImage() {
        imageViewImageToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicker();
            }
        });
    }

    private void addObservers() {
        viewModel.getPostUploadResponseMutableLiveData().observe(this, new Observer<PostUploadResponse>() {
            @Override
            public void onChanged(PostUploadResponse postUploadResponse) {
                dismissLoadingIndicator();
                if(postUploadResponse == null) {
                    makeErrorSnackBar(null, parent);
                } else if(postUploadResponse.getError() != null) {
                    makeErrorSnackBar(postUploadResponse.getError(), parent);
                } else {
                    Toast.makeText(PostImageActivity.this, postUploadResponse.getSuccess(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            this.selectedImageUri = resultUri;
            buttonPost.setEnabled(true);
            buttonPost.setTextColor(Color.BLUE);
            imageViewImageToPost.setImageURI(resultUri);
        } else if(resultCode == UCrop.RESULT_ERROR) {
            makeErrorSnackBar(UCrop.getError(data).toString(), parent);
        }
    }

    private void handleOnTapPost() {
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingIndicator();
                viewModel.postImage(selectedImageUri, editTextDescription.getText().toString());
            }
        });
    }

    private void handleCloseButton() {
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}