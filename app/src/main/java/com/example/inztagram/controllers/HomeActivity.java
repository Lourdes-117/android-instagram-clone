package com.example.inztagram.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.inztagram.R;
import com.example.inztagram.utility.InztaAppCompatActivity;

public class HomeActivity extends InztaAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}