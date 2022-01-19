package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class peg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peg);


        View wip = findViewById(R.id.wip);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        wip.startAnimation(fade);
    }
}