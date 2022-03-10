package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
    }

    public void signIn(View view) {

        EditText usernameView = findViewById(R.id.usernameLogin);
        EditText passwordView = findViewById(R.id.passwordLogin);

        String usernameText = usernameView.getText().toString();
        String passwordText = passwordView.getText().toString();

        if (usernameText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "You must fill all.", Toast.LENGTH_SHORT).show();
        } else if (dbHelper.checkUsername(usernameText)) {
            Toast.makeText(this, "User doesn't exist.", Toast.LENGTH_SHORT).show();
        } else if (!dbHelper.checkLogin(usernameText, passwordText)) {
            Toast.makeText(this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Welcome back " + usernameText + ".", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(this, Menu.class);
            myIntent.putExtra("username", usernameText);
            startActivity(myIntent);

        }

    }

    public void signUp(View view) {
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}