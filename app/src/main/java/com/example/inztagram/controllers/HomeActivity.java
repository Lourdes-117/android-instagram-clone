package com.example.inztagram.controllers;

import android.os.Bundle;
import com.example.inztagram.R;
import com.example.inztagram.utility.InztaAppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeActivity extends InztaAppCompatActivity {
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setElements();
    }

    private void setElements() {
        bottomNavigationView = findViewById(R.id.botom_navigation);
    }
}