package com.example.inztagram.utility;

import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class InztaAppCompatActivity extends AppCompatActivity {

    public void makeErrorSnackBar(String errorTextString, View view) {
        String messageToShow = "Call Failed. Please try again later";
        if(errorTextString != null) {
            messageToShow = errorTextString;
        }
        Snackbar.make(view, messageToShow, Snackbar.LENGTH_SHORT)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }).show();
    }
}
