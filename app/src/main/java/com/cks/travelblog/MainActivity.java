package com.cks.travelblog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = this.getSharedPreferences("com.cks.travelblog", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("loginState", false)) {
            Intent intent = new Intent(this, BlogListActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar2);

        nameEditText.addTextChangedListener(createTextWatcher(nameEditText));
        passwordEditText.addTextChangedListener(createTextWatcher(passwordEditText));
    }

    public void onLoginClicked(View view) {
        String username = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty()) {
            nameEditText.setError("Username must not be empty!");
        } else if (password.isEmpty()) {
            passwordEditText.setError("Password must not be empty!");
        } else if (!username.equals("admin") || !password.equals("admin")) {
            showErrorDialog();
        } else {
            performLogin();
        }

    }

    private TextWatcher createTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editText.setError(null);
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or password is incorrect. Please try again!")
                .setPositiveButton("OK", null)
                .show();
    }

    private void performLogin() {
        sharedPreferences.edit().putBoolean("loginState", true).apply();

        nameEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        loginButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, BlogListActivity.class);
            startActivity(intent);
        }, 2000);
    }
}