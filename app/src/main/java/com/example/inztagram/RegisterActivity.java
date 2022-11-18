package com.example.inztagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inztagram.Models.UserRegisterRequest;
import com.example.inztagram.Models.UserRegisterResponse;
import com.example.inztagram.viewModels.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputUserName, textInputFullName, textInputEmailID, textInputPassword;
    private Button registerButton;
    private TextView textViewLogin;
    private RelativeLayout parent;

    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViewModel();

        setElements();
        handleOnClickListeners();
    }

    private void initViewModel() {
        this.viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        viewModel.getCreateUserObserver().observe(this, new Observer<UserRegisterResponse>() {
            @Override
            public void onChanged(UserRegisterResponse userRegisterResponse) {
                if((userRegisterResponse == null)) {
                    RegisterActivity.this.onUserRegistrationFailed(null);
                } else {
                    if(userRegisterResponse.getError() != null) {
                        RegisterActivity.this.onUserRegistrationFailed(userRegisterResponse.getError());
                    } else {
                        RegisterActivity.this.onUserRegistrationSuccessful();
                    }
                }
            }
        });
    }

    private void setElements() {
        textInputUserName = findViewById(R.id.textInput_userName);
        textInputFullName = findViewById(R.id.textInput_fullName);
        textInputEmailID = findViewById(R.id.textInput_emailID);
        textInputPassword = findViewById(R.id.textInput_password);
        registerButton = findViewById(R.id.button_register);
        textViewLogin = findViewById(R.id.textView_login);
        parent = findViewById(R.id.parent);
    }

    private void handleOnClickListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = textInputUserName.getText().toString();
                String emailId = textInputEmailID.getText().toString();
                String password = textInputPassword.getText().toString();
                String fullName = textInputFullName.getText().toString();

                UserRegisterRequest userRegisterRequest = new UserRegisterRequest(userName, fullName, emailId, password);

                String registerError = viewModel.getRegisterErrorsIfAvailable(userRegisterRequest);
                if(registerError == null) {
                    viewModel.createNewUser(userRegisterRequest);
                } else {
                    Snackbar.make(parent, registerError, Snackbar.LENGTH_SHORT)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                }
            }
        });
    }

    private void onUserRegistrationFailed(String errorTextString) {
        String messageToShow = "Call Failed. Please try again later";
        if(errorTextString != null) {
            messageToShow = errorTextString;
        }
        Snackbar.make(RegisterActivity.this, parent, messageToShow, Snackbar.LENGTH_SHORT)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    private void onUserRegistrationSuccessful() {
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
    }
}