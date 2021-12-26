package com.demo.reversi.view.gamepages;

/**
 * Game info is encapsulated into a single class for dialog info passing
 */
public class GameInfo {
    public final int rowSize;
    public final int colSize;
    public final String playerName1;
    public final String playerName2;

    public GameInfo(String playerName1, String playerName2, int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
    }

    public GameInfo(String playerName1, String playerName2) {
        this(playerName1, playerName2, 8, 8);
    }
}
