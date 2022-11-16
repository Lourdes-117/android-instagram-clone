package com.example.inztagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inztagram.Models.RegisterModel;
import com.example.inztagram.viewModels.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText textInputUserName, textInputFullName, textInputEmailID, textInputPassword;
    private Button registerButton;
    private TextView textViewLogin;
    private RelativeLayout parent;

    private RegisterViewModel viewModel = new RegisterViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setElements();
        handleOnClickListeners();
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

                RegisterModel registerModel = new RegisterModel(userName, fullName, emailId, password);

                String registerError = viewModel.getRegisterErrorsIfAvailable(registerModel);
                if(registerError == null) {
                    Toast.makeText(RegisterActivity.this, "No Errors", Toast.LENGTH_SHORT).show();
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
}