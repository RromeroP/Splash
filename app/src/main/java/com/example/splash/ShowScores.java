package com.example.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ShowScores extends AppCompatActivity {
    String username;
    Intent intent;

    private DatabaseHelper dbHelper;

    private RecyclerView RecyclerView;
    private ArrayList<UserScore> userScores;
    private ScoresAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_show_scores);

        dbHelper = new DatabaseHelper(this);

        username = getIntent().getExtras().getString("username");

        // Initialize the RecyclerView.
        RecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        RecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize the ArrayList that will contain the data.
        userScores = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        Adapter = new ScoresAdapter(this, userScores);
        RecyclerView.setAdapter(Adapter);

        initializeData();

        int swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
                        | ItemTouchHelper.UP, swipeDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                String user_id = userScores.get(viewHolder.getAdapterPosition()).getId();
                dbHelper.deleteScore(user_id, getIntent().getExtras().getString("game"));
                userScores.remove(viewHolder.getAdapterPosition());
                Adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Toast.makeText(getApplicationContext(), "The score was deleted.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        helper.attachToRecyclerView(RecyclerView);

    }

    private void initializeData() {
        Cursor cursor;

        if (getIntent().getExtras().getString("game").equals("peg")) {
            TextView logo = findViewById(R.id.game_logo);
            logo.setText("PEG");
            logo.setBackgroundResource(R.drawable.peg_bg);

            cursor = dbHelper.getAllPeg();
        } else {
            cursor = dbHelper.getAll2048();
        }

        // Clear the existing data (to avoid duplication).
        userScores.clear();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int index_id = cursor.getColumnIndex("_id");
                int index_username = cursor.getColumnIndex("username");
                int index_score = cursor.getColumnIndex("score");
                int index_moves = cursor.getColumnIndex("moves");
                int index_time = cursor.getColumnIndex("time");

                userScores.add(new UserScore(
                        cursor.getString(index_id),
                        cursor.getString(index_username),
                        cursor.getString(index_score),
                        cursor.getString(index_moves),
                        cursor.getString(index_time)));

            }
        }

        // Notify the adapter of the change.
        Adapter.notifyDataSetChanged();
    }

    public static class UserScore {
        String id;
        String username;
        String score;
        String moves;
        String time;

        public UserScore(String id, String username, String score, String moves, String time) {
            this.id = id;
            this.username = username;
            this.score = score;
            this.moves = moves;
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getMoves() {
            return moves;
        }

        public void setMoves(String moves) {
            this.moves = moves;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}