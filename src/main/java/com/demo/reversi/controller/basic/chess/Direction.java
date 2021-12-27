package com.demo.reversi.controller.basic.chess;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UPPER_LEFT(-1, -1),
    UPPER_RIGHT(-1, 1),
    LOWER_LEFT(1, -1),
    LOWER_RIGHT(1, 1);

    private int rowOffset;
    private int columnOffset;

    private Direction(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public int[] move(int i, int j) {
        return new int[] {i + rowOffset, j + columnOffset};
    }
}
