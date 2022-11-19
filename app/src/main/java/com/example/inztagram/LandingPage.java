package com.example.inztagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LandingPage extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageView imageViewLogo, imageViewIcon;
    Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        setElements();
        setOnClickListeners();
        startAnimations();
    }

    private void setElements() {
        this.linearLayout = findViewById(R.id.linear_layout);
        this.imageViewIcon = findViewById(R.id.imageView_icon);
        this.imageViewLogo = findViewById(R.id.imageView_logo);
        this.buttonLogin = findViewById(R.id.button_login);
        this.buttonRegister = findViewById(R.id.button_register);
    }

    private void startAnimations() {
        linearLayout.animate().alpha(0f).setDuration(1);
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListeners());
        imageViewIcon.setAnimation(animation);
    }

    private void setOnClickListeners() {
        handleRegister();
        handleLogin();
    }

    private void handleRegister() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void handleLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private class MyAnimationListeners implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            imageViewIcon.clearAnimation();
            imageViewIcon.setVisibility(View.GONE);
            linearLayout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}