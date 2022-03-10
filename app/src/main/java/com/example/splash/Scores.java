package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Scores extends AppCompatActivity {
    String username;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_scores);

        username = getIntent().getExtras().getString("username");
    }

    public void on2048(View view){
        intent = new Intent(getApplicationContext(), ShowScores.class);
        intent.putExtra("username", username);
        intent.putExtra("game", "2048");
        startActivity(intent);

    }

    public void onPeg(View view){
        intent = new Intent(getApplicationContext(), ShowScores.class);
        intent.putExtra("username", username);
        intent.putExtra("game", "peg");
        startActivity(intent);
    }
}