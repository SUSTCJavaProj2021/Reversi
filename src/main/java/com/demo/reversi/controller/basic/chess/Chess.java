package com.demo.reversi.controller.basic.chess;

import java.util.Objects;
import java.util.Scanner;

public class Chess {
    private ChessColor color;
    private int rowIndex;
    private int columnIndex;

    public Chess(Chess chess) {
        this.color = chess.color;
        this.rowIndex = chess.rowIndex;
        this.columnIndex = chess.columnIndex;
    }

    public Chess(int rowIndex, int columnIndex) {
        this(ChessColor.NULL, rowIndex, columnIndex);
    }

    public Chess(ChessColor color, int rowIndex, int columnIndex) {
        this.color = color;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Chess(Scanner scanner) {
        load(scanner);
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public int[] getPosition() {
        return new int[] {rowIndex, columnIndex};
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setPosition(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public String print() {
        return color.name() + " " + rowIndex + " " + columnIndex + "\n";
    }

    public void load(Scanner scanner) {
        color = ChessColor.valueOf(scanner.next());
        rowIndex = scanner.nextInt();
        columnIndex = scanner.nextInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chess chess = (Chess) o;
        return rowIndex == chess.rowIndex && columnIndex == chess.columnIndex && color == chess.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, rowIndex, columnIndex);
    }
}
