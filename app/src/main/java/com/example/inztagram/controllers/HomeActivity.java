package com.example.inztagram.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.inztagram.Models.UserLogoutDelegate;
import com.example.inztagram.R;
import com.example.inztagram.fragments.HomeFragment;
import com.example.inztagram.fragments.MyPostsFragment;
import com.example.inztagram.fragments.ProfileFragment;
import com.example.inztagram.fragments.SearchFragment;
import com.example.inztagram.utility.InztaAppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends InztaAppCompatActivity implements UserLogoutDelegate {
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private MyPostsFragment myPostsFragment = new MyPostsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setElements();
        setOnClickListeners();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private void setElements() {
        profileFragment.delegate = this;
        bottomNavigationView = findViewById(R.id.botom_navigation);
    }

    private void setOnClickListeners() {
        handleBottomNavigationSelected();
    }

    private void handleBottomNavigationSelected() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = homeFragment;
                        break;
                    case R.id.nav_search:
                        selectorFragment = searchFragment;
                        break;
                    case R.id.nav_add:
                        selectorFragment = null;
                        startActivity(new Intent(HomeActivity.this, PostImageActivity.class));
                        break;
                    case R.id.nav_saved:
                        selectorFragment = myPostsFragment;
                        break;
                    case R.id.nav_profile:
                        selectorFragment = profileFragment;
                        break;
                }
                if(selectorFragment != null) {
                    replaceFragment(R.id.fragment_container, selectorFragment);
                }
                return selectorFragment != null;
            }
        });
    }

    @Override
    public void logoutUser() {
        Intent intent = new Intent(this, LandingPage.class);
        startActivity(intent);
        finish();
    }
}