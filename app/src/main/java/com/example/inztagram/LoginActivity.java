package com.example.inztagram;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.inztagram.Models.UserLoginRequest;
import com.example.inztagram.Models.UserLoginResponse;
import com.example.inztagram.auth.LocalAuthService;
import com.example.inztagram.utility.InztaAppCompatActivity;
import com.example.inztagram.viewModels.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends InztaAppCompatActivity {
    private Button buttonSignIn;
    private TextInputEditText textInputUserName, textInputPassword;
    private RelativeLayout parent;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setupElements();
        initViewModel();
        this.setupOnClickListeners();
    }

    private void setupElements() {
        buttonSignIn = findViewById(R.id.button_signIn);
        textInputUserName = findViewById(R.id.textInput_userName);
        textInputPassword = findViewById(R.id.textInput_password);
        parent = findViewById(R.id.parent);
    }

    private void initViewModel() {
        this.viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        this.addObservers();
    }

    private void addObservers() {
        viewModel.getCreateUserAndLoginObserver().observe(this, new Observer<UserLoginResponse>() {
            @Override
            public void onChanged(UserLoginResponse userLoginResponse) {
                LoginActivity.this.dismissLoadingIndicator();
                if(userLoginResponse == null) {
                    LoginActivity.this.makeErrorSnackBar(null, parent);
                } else {
                    if(userLoginResponse.getError() != null) {
                        LoginActivity.this.makeErrorSnackBar(userLoginResponse.getError(), parent);
                    } else if(userLoginResponse.getUuid() != null) {
                        LocalAuthService.getInstance().saveSecretKey(userLoginResponse.getUuid());
                        LoginActivity.this.handleUserLoginSuccessful();
                    } else {
                        LoginActivity.this.makeErrorSnackBar(null, parent);
                    }
                }
            }
        });
    }

    private void setupOnClickListeners() {
        this.handleLogin();
    }

    private void handleLogin() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.showLoadingIndicator();

                String userName = textInputUserName.getText().toString();
                String password = textInputPassword.getText().toString();

                UserLoginRequest userLoginRequest = new UserLoginRequest();
                userLoginRequest.setUserName(userName);
                userLoginRequest.setPassword(password);

                LoginActivity.this.viewModel.loginUser(userLoginRequest);
            }
        });
    }

    private void handleUserLoginSuccessful() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags (Intent. FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}