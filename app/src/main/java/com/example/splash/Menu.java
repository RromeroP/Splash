package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ListView listView = (ListView) findViewById(R.id.menu_list);

        String[] items = { getResources().getString(R.string.name_2048),
                getResources().getString(R.string.name_peg),
                getResources().getString(R.string.name_help)};

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                R.layout.menu_item, items);

        listView.setAdapter(adapt);


        View logo1 = findViewById(R.id.menu_icon1);
        View logo2 = findViewById(R.id.menu_icon2);
        View moon = findViewById(R.id.bottomImage);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.infinite_rotation);

        logo1.startAnimation(rotate);
        logo2.startAnimation(rotate);
        moon.startAnimation(rotate);

        getSupportActionBar().hide();
    }

    public void onClick(View v){
        Toast.makeText(this,"WIP",Toast.LENGTH_LONG).show();
    }

}