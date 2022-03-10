package com.example.splash;

public class CellModel {

    private boolean isSelected;
    private boolean isPossible;
    private int bgId;
    private int circleId;
    private int gridSize;
    private int row;
    private int col;

    public CellModel(int bgId, int circleId, int gridSize, int row, int col) {
        this.isSelected = false;
        this.isPossible = false;
        this.bgId = bgId;
        this.circleId = circleId;
        this.gridSize = gridSize;
        this.row = row;
        this.col = col;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public void setPossible(boolean possible) {
        isPossible = possible;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
