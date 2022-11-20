package com.example.inztagram.controllers;

import com.example.inztagram.R;
import com.example.inztagram.utility.InztaAppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.yalantis.ucrop.UCrop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.UUID;

public class PostImageActivity extends InztaAppCompatActivity {
    TextView textViewPost;
    ImageView imageViewImageToPost;
    EditText editTextDescription;
    ImageView imageViewClose;
    ActivityResultLauncher<String>  mGetContent;
    LinearLayout parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

        setElements();
        setOnClickListeners();

        mGetContent.launch("image/*");
   }

    private void setElements() {
        textViewPost = findViewById(R.id.textView_post);
        imageViewImageToPost = findViewById(R.id.imageView_toPost);
        imageViewClose = findViewById(R.id.imageView_close);
        editTextDescription = findViewById(R.id.editText_description);
        parent = findViewById(R.id.parent);
    }

    private void setOnClickListeners() {
        handleCloseButton();
        handleImageSelected();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            imageViewImageToPost.setImageURI(resultUri);
        } else if(resultCode == UCrop.RESULT_ERROR) {
            makeErrorSnackBar(UCrop.getError(data).toString(), parent);
        }
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