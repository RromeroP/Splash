package com.example.splash;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CellAdapter extends ArrayAdapter<CellModel> {
    int[][] grid;
    boolean selected = false;

    Context context;
    ViewGroup viewGroup;

    TextView move_counter;
    int moves = 0;
    Chronometer timer;
    String username;

    public CellAdapter(@NonNull Context context, ArrayList<CellModel> cellModelArrayList,
                       int[][] selectedGrid, TextView move_count, Chronometer time, String user) {
        super(context, 0, cellModelArrayList);
        this.context = context;

        grid = selectedGrid;
        move_counter = move_count;
        timer = time;
        checkWin();
        username = user;

        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        viewGroup = parent;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.cells, parent, false);
        }

        CellModel cellModel = getItem(position);

        CardView cellCard = listItemView.findViewById(R.id.cellCard);
        ImageView cellBg = listItemView.findViewById(R.id.cellBg);
        ImageView cellCircle = listItemView.findViewById(R.id.cellCircle);

        //We check the grid size and adjust its dimensions
        if (cellModel.getGridSize() == 8) {
            int cellSize = (int) getContext().getResources().getDimension(R.dimen.peg_size_sm);
            cellCard.setLayoutParams(new ViewGroup.LayoutParams(cellSize, cellSize));
        } else if (cellModel.getGridSize() == 9) {
            int cellSize = (int) getContext().getResources().getDimension(R.dimen.peg_size_sm2);
            cellCard.setLayoutParams(new ViewGroup.LayoutParams(cellSize, cellSize));
        }

        //We check whether it should be and empty cell or not
        if (cellModel.getCircleId() == 1) {
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));
        } else {
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
        }

        //We check whether it should be a cell (we add a listener if it is) or not
        if (cellModel.getBgId() == 1) {
            cellBg.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_bg));

            listItemView.setOnClickListener(view -> checkClick(cellModel, cellCircle));

        } else {
            cellCard.setVisibility(View.INVISIBLE);
        }

        return listItemView;
    }

    private void checkClick(CellModel cellModel, ImageView cellCircle) {
        Animation select = AnimationUtils.loadAnimation(cellCircle.getContext(), R.anim.select);
        Animation deselect = AnimationUtils.loadAnimation(cellCircle.getContext(), R.anim.deselect);

        if (cellModel.isPossible()) {
            //We make the move and delete all other suggestions
            checkDirection(cellModel);
            removePossible();
        } else if (selected && cellModel.isSelected()) {
            //Deselect selected cell
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));

            checkDirection(cellModel);

            cellModel.setSelected(false);
            selected = false;

            cellCircle.startAnimation(deselect);

        } else if (!selected && grid[cellModel.getRow()][cellModel.getCol()] == 1) {
            //Select valid cell
            cellCircle.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_selected));

            checkDirection(cellModel);

            cellModel.setSelected(true);
            selected = true;

            cellCircle.startAnimation(select);

        }
    }

    private void checkDirection(CellModel cellModel) {
        //Check if the 2 top cells are not out of bounds
        boolean topBounds = cellModel.getRow() - 1 >= 0 && cellModel.getRow() - 2 >= 0;

        //Check if the 2 bottom cells are not out of bounds
        boolean botBounds = cellModel.getRow() + 1 < grid.length && cellModel.getRow() + 2 < grid.length;

        //Check if the 2 left cells are not out of bounds
        boolean leftBounds = cellModel.getCol() - 1 >= 0 && cellModel.getCol() - 2 >= 0;

        //Check if the 2 right cells are not out of bounds
        boolean rightBounds = cellModel.getCol() + 1 < grid.length && cellModel.getCol() + 2 < grid.length;

        if (topBounds) {
            checkPossibles(cellModel, "UP");
        }

        if (botBounds) {
            checkPossibles(cellModel, "DOWN");
        }

        if (leftBounds) {
            checkPossibles(cellModel, "LEFT");
        }

        if (rightBounds) {
            checkPossibles(cellModel, "RIGHT");
        }
    }

    private void checkPossibles(CellModel cellModel, String direction) {
        int y = 0;
        int x = 0;

        switch (direction) {
            case "UP":
                y = -1;
                break;
            case "DOWN":
                y = 1;
                break;
            case "LEFT":
                x = -1;
                break;
            case "RIGHT":
                x = 1;
                break;
            default:
                break;
        }

        //We get the value on the grid
        int pos1 = grid[cellModel.getRow() + y][cellModel.getCol() + x];
        int pos2 = grid[cellModel.getRow() + (y * 2)][cellModel.getCol() + (x * 2)];

        //We get the index and using it we get the cell
        int index = grid.length * (cellModel.getRow()) + (cellModel.getCol());
        int index1 = grid.length * (cellModel.getRow() + y) + (cellModel.getCol() + x);
        int index2 = grid.length * (cellModel.getRow() + (y * 2)) + (cellModel.getCol() + (x * 2));

        ImageView imageView = viewGroup.getChildAt(index).findViewById(R.id.cellCircle);
        ImageView imageView1 = viewGroup.getChildAt(index1).findViewById(R.id.cellCircle);
        ImageView imageView2 = viewGroup.getChildAt(index2).findViewById(R.id.cellCircle);

        CellModel cell = getItem(index);
        CellModel cell1 = getItem(index1);
        CellModel cell2 = getItem(index2);


        if (pos1 == 1 && pos2 == 2) {
            //We mark all possible moves
            if (!selected) {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_possible));
                Animation possible = AnimationUtils.loadAnimation(imageView2.getContext(), R.anim.possible);
                imageView2.startAnimation(possible);
                cell2.setPossible(true);
            } else {
                imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
                cell2.setPossible(false);
            }
        }

        //We move the token 2 positions a remove de middle one
        if (cell2.isSelected() && cell.isPossible()) {
            imageView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_token));

            grid[cellModel.getRow()][cellModel.getCol()] = 1;
            grid[cellModel.getRow() + y][cellModel.getCol() + x] = 2;
            grid[cellModel.getRow() + (y * 2)][cellModel.getCol() + (x * 2)] = 2;

            selected = false;

            cell.setSelected(false);
            cell.setPossible(false);

            cell1.setSelected(false);
            cell1.setPossible(false);

            cell2.setSelected(false);
            cell2.setPossible(false);

            Animation move = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.move);
            Animation move2 = AnimationUtils.loadAnimation(imageView.getContext(), R.anim.move2);

            imageView.startAnimation(move);
            imageView1.startAnimation(move2);
            imageView2.startAnimation(move2);

            addMove();
            checkWin();

            move2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageView1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
                    imageView2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

    }

    private void removePossible() {
        CellModel cell;
        for (int i = 0; i < grid.length * grid.length; i++) {
            cell = getItem(i);
            if (cell.isPossible()) {
                cell.setPossible(false);
                viewGroup.getChildAt(i).findViewById(R.id.cellCircle).setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.cell_transparent));
            }
        }
    }

    private void addMove() {
        moves++;
        move_counter.setText(moves + " Moves");
    }

    private void checkWin() {
        int possible_moves = 0;
        int ones = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 1) ones++;
                if (i - 2 >= 0 && j - 2 >= 0 &&
                        i + 2 < grid.length && j + 2 < grid.length) {
                    possible_moves += checking(i, j, 1, 0) +
                            checking(i, j, -1, 0) +
                            checking(i, j, 0, 1) +
                            checking(i, j, 0, -1);


                }
            }
        }

        if (possible_moves == 0) {
            Intent intent = new Intent(getContext(), GameOver.class);

            if (ones == 1) {
                intent.putExtra("title", "YOU WIN");
                intent.putExtra("bonus", 1.5);
            } else {
                intent.putExtra("title", "YOU LOSE");
                intent.putExtra("bonus", 1.0);
            }

            intent.putExtra("score", 0);
            intent.putExtra("game", "peg");
            intent.putExtra("moves", moves);
            intent.putExtra("username", username);

            long elapsedMillis = SystemClock.elapsedRealtime() - timer.getBase();
            intent.putExtra("ms", elapsedMillis);

            timer.stop();
            context.startActivity(intent);
        }

    }

    private int checking(int i, int j, int x, int y) {
        int value1 = grid[i][j];
        int value2 = grid[i + x][j + y];
        int value3 = grid[i + (x * 2)][j + (y * 2)];

        if (value1 == 1 && value2 == 1 && value3 == 2) {
            return 1;
        } else {
            return 0;
        }
    }
}

