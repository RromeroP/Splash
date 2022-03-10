package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Menu extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_menu);

        username = getIntent().getExtras().getString("username");

        ListView listView = (ListView) findViewById(R.id.menu_list);

        String[] items = {getResources().getString(R.string.name_2048),
                getResources().getString(R.string.name_peg),
                getResources().getString(R.string.name_scores),
                getResources().getString(R.string.name_settings)
        };

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this,
                R.layout.menu_item, items);

        listView.setAdapter(adapt);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        intent = new Intent(getApplicationContext(), a_2048.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), MainPeg.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), Scores.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        break;
                    case 3:
                        System.out.println("Settings");
                        break;

                }
            }
        });

        View logo1 = findViewById(R.id.menu_icon1);
        View logo2 = findViewById(R.id.menu_icon2);
        View moon = findViewById(R.id.bottomImage);

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.infinite_rotation);
        Animation rotate2 = AnimationUtils.loadAnimation(this, R.anim.infinite_rotation2);

        logo1.startAnimation(rotate);
        logo2.startAnimation(rotate);
        moon.startAnimation(rotate2);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}