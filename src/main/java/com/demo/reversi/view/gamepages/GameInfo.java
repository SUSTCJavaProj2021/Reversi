package com.demo.reversi.view.gamepages;

import com.demo.reversi.controller.interfaces.Difficulty;

/**
 * Game info is encapsulated into a single class for dialog info passing
 */
public class GameInfo {
    public final int rowSize;
    public final int colSize;
    public final String playerName1;
    public final boolean isPlayer1AI;
    public final Difficulty player1Difficulty;
    public final String playerName2;
    public final boolean isPlayer2AI;
    public final Difficulty player2Difficulty;

    public GameInfo(String playerName1, boolean isPlayer1AI, Difficulty player1Difficulty, String playerName2, boolean isPlayer2AI, Difficulty player2Difficulty, int rowSize, int colSize) {
        if (rowSize <= 0) {
            this.rowSize = 8;
        } else {
            this.rowSize = rowSize;
        }
        if (colSize <= 0) {
            this.colSize = 8;
        } else {
            this.colSize = colSize;
        }

        this.playerName1 = playerName1;
        this.isPlayer1AI = isPlayer1AI;
        this.player1Difficulty = player1Difficulty;

        this.playerName2 = playerName2;
        this.isPlayer2AI = isPlayer2AI;
        this.player2Difficulty = player2Difficulty;
    }

    public GameInfo(String playerName1, String playerName2) {
        this(playerName1, false, Difficulty.EASY, playerName2, false, Difficulty.EASY, 8, 8);
    }

    public GameInfo(String playerName1, String playerName2, int rowSize, int colSize) {
        this(playerName1, false, Difficulty.EASY, playerName2, false, Difficulty.EASY, rowSize, colSize);
    }
}
