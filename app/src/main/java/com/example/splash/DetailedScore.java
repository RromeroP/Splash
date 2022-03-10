package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DetailedScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_detailed_score);

        TextView usernameText = findViewById(R.id.detailedUsername);
        TextView scoreText = findViewById(R.id.detailedScore);
        TextView moveText = findViewById(R.id.detailedMoves);
        TextView timeText = findViewById(R.id.detailedTimes);

        usernameText.setText(getIntent().getExtras().getString("username"));
        scoreText.setText(getIntent().getExtras().getString("score"));
        moveText.setText(getIntent().getExtras().getString("moves"));
        timeText.setText(getIntent().getExtras().getString("time"));

        getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
    }
}