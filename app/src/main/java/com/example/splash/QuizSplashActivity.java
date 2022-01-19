package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class QuizSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_splash);

        View title1 = findViewById(R.id.title_1);
        View logo = findViewById(R.id.logo);
        View title2 = findViewById(R.id.title_2);
        View version = findViewById(R.id.version);

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fade2 = AnimationUtils.loadAnimation(this, R.anim.fade_in2);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_fade_in);

        title1.startAnimation(fade2);
        logo.startAnimation(rotate);
        title2.startAnimation(fade2);
        version.startAnimation(fade);


        fade2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent myIntent = new Intent(QuizSplashActivity.this, Menu.class);
                startActivity(myIntent);
                overridePendingTransition(0, R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        getSupportActionBar().hide();
    }
}