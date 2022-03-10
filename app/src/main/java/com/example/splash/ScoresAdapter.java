package com.example.splash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    private ArrayList<ShowScores.UserScore> userScores;
    private Context context;

    ScoresAdapter(Context context, ArrayList<ShowScores.UserScore> userScores) {
        this.userScores = userScores;
        this.context = context;
    }

    @NonNull
    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowScores.UserScore currentScore = userScores.get(position);

        holder.bindTo(currentScore);
    }

    @Override
    public int getItemCount() {
        return userScores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView username;
        private TextView score;

        ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.list_username);
            score = itemView.findViewById(R.id.list_score);

            itemView.setOnClickListener(this);
        }

        void bindTo(ShowScores.UserScore currentScore){
            username.setText(currentScore.getUsername());
            score.setText(currentScore.getScore());
        }

        @Override
        public void onClick(View view) {
            ShowScores.UserScore currentScore = userScores.get(getAdapterPosition());
            Intent intent= new Intent(context, DetailedScore.class);
            intent.putExtra("username", currentScore.getUsername());
            intent.putExtra("score", currentScore.getScore());
            intent.putExtra("moves", currentScore.getMoves());
            intent.putExtra("time", currentScore.getTime());
            context.startActivity(intent);
        }
    }
}
