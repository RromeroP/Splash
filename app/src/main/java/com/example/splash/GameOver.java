package com.example.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GameOver extends AppCompatActivity {
    String username;
    Intent intent;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_game_over);

        dbHelper = new DatabaseHelper(this);

        TextView title = findViewById(R.id.gameOverTitle);
        TextView scoreText = findViewById(R.id.scoreGameOver);
        TextView moveText = findViewById(R.id.movesGameOver);
        TextView timeText = findViewById(R.id.timeGameOver);

        int minimumMoves = 300;
        int minimumTime = 100000;

        long score = getIntent().getExtras().getInt("score");
        int moves = getIntent().getExtras().getInt("moves");
        String game = getIntent().getExtras().getString("game");
        double bonus = getIntent().getExtras().getDouble("bonus");
        username = getIntent().getExtras().getString("username");
        long ms = getIntent().getExtras().getLong("ms");

        String time = String.format(Locale.ENGLISH, "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(ms),
                TimeUnit.MILLISECONDS.toSeconds(ms) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms)));

        if (game.equals("peg")) {
            score = ((minimumTime / ms) * (minimumMoves / moves)) * 100;
        }

        int final_score = (int) (score * bonus);

        scoreText.setText("SCORE: " + final_score);
        moveText.setText("MOVES: " + moves);
        title.setText(getIntent().getExtras().getString("title"));
        timeText.setText("TIME: " + time);
        if (game.equals("2048")) {
            dbHelper.insertData2048(username, final_score, String.valueOf(moves), time);
        } else if (game.equals("peg")) {
            dbHelper.insertDataPeg(username, final_score, String.valueOf(moves), time);
        }

        getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
    }

    public void goMenu(View view) {
        intent = new Intent(getApplicationContext(), Menu.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void goScore(View view) {
        intent = new Intent(getApplicationContext(), Scores.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }
}