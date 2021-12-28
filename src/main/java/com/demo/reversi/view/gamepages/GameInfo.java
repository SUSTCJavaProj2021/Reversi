package com.demo.reversi.view.gamepages;

import com.demo.reversi.controller.basic.player.Mode;

/**
 * Game info is encapsulated into a single class for dialog info passing
 */
public class GameInfo {
//    public static final int BOARD_MIN_SIZE = 8;

    public final int rowSize;
    public final int colSize;
    public final String playerName1;
    public final boolean isPlayer1AI;
    public final Mode player1Mode;
    public final String playerName2;
    public final boolean isPlayer2AI;
    public final Mode player2Mode;

    public GameInfo(String playerName1, boolean isPlayer1AI, Mode player1Mode, String playerName2, boolean isPlayer2AI, Mode player2Mode, int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;

        this.playerName1 = playerName1;
        this.isPlayer1AI = isPlayer1AI;
        this.player1Mode = player1Mode;

        this.playerName2 = playerName2;
        this.isPlayer2AI = isPlayer2AI;
        this.player2Mode = player2Mode;
    }

    public GameInfo(String playerName1, String playerName2) {
        this(playerName1, false, Mode.EASY, playerName2, false, Mode.EASY, 8, 8);
    }

    public GameInfo(String playerName1, String playerName2, int rowSize, int colSize) {
        this(playerName1, false, Mode.EASY, playerName2, false, Mode.EASY, rowSize, colSize);
    }
}
