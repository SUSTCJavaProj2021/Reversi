package com.demo.reversi.controller.basic.game;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.chess.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    public static final int MAX_SIZE = 20;

    private int rowSize;
    private int columnSize;
    private final Chess[][] chess;
    private final boolean[][] banned;
    private final List<int[]> bannedPositions;

    public Board(Board board) {
        this.rowSize = board.rowSize;
        this.columnSize = board.columnSize;
        this.chess = new Chess[MAX_SIZE][MAX_SIZE];
        this.banned = new boolean[MAX_SIZE][MAX_SIZE];
        this.bannedPositions = new ArrayList<>(board.bannedPositions);

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (board.chess[i][j] != null) {
                    this.chess[i][j] = new Chess(board.chess[i][j]);
                }
            }
        }

        for (int[] position: bannedPositions) {
            banned[position[0]][position[1]] = true;
        }
    }

    public Board(int rowSize, int columnSize, List<Chess> chessList, List<int[]> bannedPositions) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        chess = new Chess[MAX_SIZE][MAX_SIZE];
        banned = new boolean[MAX_SIZE][MAX_SIZE];
        this.bannedPositions = bannedPositions;

        for (int[] position : bannedPositions) {
            int i = position[0], j = position[1];
            banned[i][j] = true;
        }

        for (Chess chess : chessList) {
            this.chess[chess.getRowIndex()][chess.getColumnIndex()] = chess;
        }

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (!banned[i][j] && chess[i][j] == null) {
                    chess[i][j] = new Chess(i, j);
                }
            }
        }
    }

    public Board(Scanner scanner) {
        chess = new Chess[MAX_SIZE][MAX_SIZE];
        banned = new boolean[MAX_SIZE][MAX_SIZE];
        bannedPositions = new ArrayList<>();
        load(scanner);
    }

    public Chess[][] getChess() {
        return chess;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public boolean setSize(int rowSize, int columnSize) {
        if (rowSize < this.rowSize || rowSize > MAX_SIZE) {
            return false;
        } else if (columnSize < this.columnSize || columnSize > MAX_SIZE) {
            return false;
        } else {
            int oldRowSize = this.rowSize, oldColumnSize = this.columnSize;
            this.rowSize = rowSize;
            this.columnSize = columnSize;

            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < columnSize; j++) {
                    if (i >= oldRowSize || j >= oldColumnSize) {
                        chess[i][j] = new Chess(i, j);
                    }
                }
            }

            return true;
        }
    }

    public boolean isValid(int i, int j) {
        return i >= 0 && i < rowSize && j >= 0 && j < columnSize && !banned[i][j];
    }

    public boolean isCaptured(int i, int j) {
        return chess[i][j].getColor() != ChessColor.NULL;
    }

    public List<int[]> getBannedPositions() {
        return bannedPositions;
    }

    private boolean isDifferentColor(Chess chess1, Chess chess2) {
        return chess1.getColor() != ChessColor.NULL && chess2.getColor() != ChessColor.NULL &&
            chess1.getColor() != chess2.getColor();
    }

    /*
    todo: The method checkEnd is slow.
    */
    public List<Chess[]> checkPosition(Chess chess, boolean cheatMode) {
        int i = chess.getRowIndex(), j = chess.getColumnIndex();

        if (!isValid(i, j) || isCaptured(i, j)) {
            return new ArrayList<>();
        } else {
            List<Chess[]> chessList = new ArrayList<>();

            for (Direction direction : Direction.values()) {
                int[] position = direction.move(i, j);

                if (!isValid(position[0], position[1])) {
                    continue;
                }

                Chess curChess = this.chess[position[0]][position[1]];
                if (isDifferentColor(chess, curChess)) {
                    List<Chess[]> tempChessList = new ArrayList<>(0);

                    for (; isValid(position[0], position[1]); position = direction.move(position[0], position[1])) {
                        curChess = this.chess[position[0]][position[1]];

                        if (curChess.getColor() == ChessColor.NULL) {
                            tempChessList.clear();
                            break;
                        } else if (curChess.getColor() == chess.getColor()) {
                            break;
                        } else {
                            Chess newChess = new Chess(chess.getColor(), position[0], position[1]);

                            tempChessList.add(new Chess[] {curChess, newChess});
                        }
                    }

                    if (!isValid(position[0], position[1])) {
                        tempChessList.clear();
                    }

                    chessList.addAll(tempChessList);
                }
            }

            if (!cheatMode && chessList.isEmpty()) {
                return chessList;
            }

            chessList.add(new Chess[] {this.chess[i][j], chess});
            return chessList;
        }
    }

    public List<Chess[]> addChess(Chess chess, boolean cheatMode) {
        List<Chess[]> list = checkPosition(chess, cheatMode);

        if (!list.isEmpty()) {
            changeInto(list);
        }

        return list;
    }

    public List<int[]> showAllPossibleMoves(ChessColor color, boolean cheatMode) {
        List<int[]> moves = new ArrayList<>();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (!banned[i][j] && chess[i][j].getColor() == ChessColor.NULL) {
                    Chess tempChess = new Chess(i, j);
                    tempChess.setColor(color);

                    if (!checkPosition(tempChess, cheatMode).isEmpty()) {
                        moves.add(new int[] {i, j});
                    }
                }
            }
        }

        return moves;
    }

    protected boolean checkMovable(ChessColor color, boolean cheatMode) {
        return !showAllPossibleMoves(color, cheatMode).isEmpty();
    }

    public ChessColor calculateWinner() {
        int[] numberOfChess = new int[ChessColor.values().length];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (!banned[i][j]) {
                    ++numberOfChess[chess[i][j].getColor().ordinal()];
                }
            }
        }

        if (numberOfChess[0] > numberOfChess[1]) {
            return ChessColor.BLACK;
        } else if (numberOfChess[0] < numberOfChess[1]) {
            return ChessColor.WHITE;
        } else {
            return ChessColor.NULL;
        }
    }

    public void changeInto(List<Chess[]> list) {
        for (Chess[] modifiedChess: list) {
            Chess newChess = modifiedChess[1];
            int i = newChess.getRowIndex(), j = newChess.getColumnIndex();

            chess[i][j] = newChess;
        }
    }

    public void changeBack(List<Chess[]> list) {
        for (Chess[] modifiedChess: list) {
            Chess newChess = modifiedChess[0];
            int i = newChess.getRowIndex(), j = newChess.getColumnIndex();

            chess[i][j] = newChess;
        }
    }

    public void printToCmd() {
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (banned[i][j]) {
                    System.out.printf("%3c", '#');
                    continue;
                }

                int printColor = chess[i][j].getColor() == ChessColor.NULL ? 0 :
                    chess[i][j].getColor() == ChessColor.BLACK ? -1 : 1;
                System.out.printf("%3d", printColor);
            }
            System.out.println();
        }
    }

    public String print() {
        StringBuilder string = new StringBuilder();

        string.append(rowSize).append(' ').append(columnSize).append(' ').append('\n');

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                string.append(banned[i][j] ? "BANNED " : chess[i][j].getColor().name() + " ");
            }
            string.append('\n');
        }

        return string.toString();
    }

    public void load(Scanner scanner) {
        rowSize = scanner.nextInt();
        columnSize = scanner.nextInt();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                String type = scanner.next();

                if (type.equals("BANNED")) {
                    banned[i][j] = true;
                    bannedPositions.add(new int[] {i, j});
                } else {
                    chess[i][j] = new Chess(ChessColor.valueOf(type), i, j);
                }
            }
        }
    }
}
