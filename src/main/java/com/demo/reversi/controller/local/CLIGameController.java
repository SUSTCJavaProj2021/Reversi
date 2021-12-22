package com.demo.reversi.controller.local;

import com.demo.reversi.controller.GridStatus;
import com.demo.reversi.controller.PlayerConstants;
import com.demo.reversi.logger.Log0j;

public class CLIGameController {

    public final int rowSize;
    public final int colSize;
    public int board[][];
    public PlayerConstants currentPlayer;

    public CLIGameController(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;

        board = new int[rowSize][colSize];
        board[rowSize / 2 - 1][colSize / 2 - 1] = board[rowSize / 2][colSize / 2] = -1;
        board[rowSize / 2 - 1][colSize / 2] = board[rowSize / 2][colSize / 2 - 1] = 1;

        //TEST
        board[1][1] = -1;
        board[2][2] = -1;
        board[4][4] = -1;
        board[5][5] = -1;
        board[6][6] = -1;
        board[7][7] = 1;
        //END TEST


        currentPlayer = PlayerConstants.BLACK_PLAYER;

    }

    public GridStatus getBlockStatus(int rowIndex, int colIndex) {
        if (board[rowIndex][colIndex] == 1) {
            return GridStatus.PLAYER_1;
        } else if (board[rowIndex][colIndex] == -1) {
            return GridStatus.PLAYER_2;
        } else {
            return GridStatus.UNOCCUPIED;
        }
    }

    public PlayerConstants getCurrentPlayer() {
        return currentPlayer;
    }


    public static final boolean MOVE_MODIFY = true;
    public static final boolean MOVE_DETECT_ONLY = false;
    public static final boolean MOVE_INVALID = false;
    public static final boolean MOVE_VALID = true;
    public static final boolean MOVE_COMPLETE = true;

    /**
     * @param rowIndex Row index
     * @param colIndex Column index
     * @return POS_VALID, POS_INVALID
     */
    public boolean checkMove(int rowIndex, int colIndex) {
        // TODO: Check the position.
        if(board[rowIndex][colIndex]!=0){
            return MOVE_INVALID;
        }
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (makeOneDirectMove(rowIndex, colIndex, i, j, currentPlayer == PlayerConstants.BLACK_PLAYER ? 1 : -1, MOVE_DETECT_ONLY) == MOVE_VALID) {
                    return MOVE_VALID;
                }
            }
        }
        return MOVE_INVALID;
    }

    /**
     * @param rowIndex Row index
     * @param colIndex Column index
     * @return MOVE_INVALID, MOVE_COMPLETE
     */
    public boolean makeMove(int rowIndex, int colIndex) {
        if(board[rowIndex][colIndex]!=0){
            return MOVE_INVALID;
        }

        // TODO: Judge moves, then make the move.

        // TODO: If the game is currently undoing moves, cancel all the moves before.

        int cnt = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                cnt += makeOneDirectMove(rowIndex, colIndex, i, j, currentPlayer == PlayerConstants.BLACK_PLAYER ? 1 : -1, MOVE_MODIFY) == MOVE_COMPLETE ? 1 : 0;
            }
        }
        if (cnt == 0) {
            return MOVE_INVALID;
        }
        board[rowIndex][colIndex] = currentPlayer == PlayerConstants.BLACK_PLAYER ? 1 : -1;
//        printBoard();
        makeTurn();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (checkMove(i, j)) {
                    return MOVE_COMPLETE;
                }
            }
        }
        makeTurn();
        return MOVE_COMPLETE;
    }

    /**
     * @param rowIndex    Row Index
     * @param colIndex    Column Index
     * @param stepRow     If stepRow = 1, then the method head rightwards.
     * @param stepCol     If stepCol = 1, then the method head downwards.
     * @param isModifying If MOVE_MODIFY, modify when performing detecting. If
     *                    MOVE_DETECT_ONLY, return immediately if the move is valid.
     * @return MOVE_INVALID, MOVE_COMPLETE
     */
    private boolean makeOneDirectMove(int rowIndex, int colIndex, int stepRow, int stepCol, int currentPlayer,
                                      boolean isModifying) {

        // If the selected move is heading toward the boundary
        if (rowIndex + 2 * stepRow < 0 || rowIndex + 2 * stepRow > rowSize - 1 || colIndex + 2 * stepCol < 0
                || colIndex + 2 * stepCol > colSize - 1) {
            return MOVE_INVALID;
        }

        // Cursor u, v
        int u = rowIndex, v = colIndex;
        u += stepRow;
        v += stepCol;
        if (board[u][v] == currentPlayer || board[u][v] == 0) {
            return MOVE_INVALID;
        }

        while (u + stepRow <= rowSize - 1 && u + stepRow >= 0 && v + stepCol >= 0 && v + stepCol <= colSize - 1) {
            u += stepRow;
            v += stepCol;

            // If an empty position is encountered
            if (board[u][v] == 0) {
                return MOVE_INVALID;
            }

            if (board[u][v] == currentPlayer) {
                if (isModifying == MOVE_DETECT_ONLY) {
                    return MOVE_VALID;
                }
                int i = rowIndex, j = colIndex;
                while (i + stepRow <= rowSize - 1 && i + stepRow >= 0 && j + stepCol >= 0 && j + stepCol <= colSize - 1) {
                    i += stepRow;
                    j += stepCol;
                    if (i == u && j == v) {
                        break;
                    }
                    board[i][j] = currentPlayer;
                }
                return MOVE_COMPLETE;
            }
        }

        return MOVE_INVALID;
    }

    public void makeTurn() {
        currentPlayer = (currentPlayer == PlayerConstants.BLACK_PLAYER) ? PlayerConstants.WHITE_PLAYER
                : PlayerConstants.BLACK_PLAYER;
    }

    public void printBoard() {
        Log0j.writeInfo("Printing Board:");
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                System.out.printf("%2d", board[i][j]);
            }
            System.out.println();
        }
    }
}
