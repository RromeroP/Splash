package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);
    }

    public void signUp(View view) {
        EditText usernameView = findViewById(R.id.username_value);
        EditText passwordView = findViewById(R.id.password_value);
        EditText repeatView = findViewById(R.id.repeat_value);

        String usernameText = usernameView.getText().toString();
        String passwordText = passwordView.getText().toString();
        String repeatText = repeatView.getText().toString();

        if (usernameText.isEmpty() || passwordText.isEmpty() || repeatText.isEmpty()) {
            Toast.makeText(this, "You must fill all.", Toast.LENGTH_SHORT).show();
        } else if (!dbHelper.checkUsername(usernameText)) {
            Toast.makeText(this, "Username already in use.", Toast.LENGTH_SHORT).show();
        } else if (!passwordText.equals(repeatText)) {
            Toast.makeText(this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertUser(usernameText, passwordText);
            Toast.makeText(this, "Welcome " + usernameText + ".", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, Login.class);
            startActivity(myIntent);
        }

    }
}