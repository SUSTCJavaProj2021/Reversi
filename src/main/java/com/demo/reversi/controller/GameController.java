package com.demo.reversi.controller;

import com.demo.reversi.view.Updatable;
import javafx.beans.property.ObjectProperty;

public interface GameController {

    public boolean bindToGamePage(Updatable gamePage);

//    public boolean bindToChessBoard();

    public int getRowSize();

    public int getColSize();

    public boolean onGridClick(int row, int col);

    public void forceUpdateGUI();

    public Player getWhitePlayer();

    public Player getBlackPlayer();

    public Player getCurrentPlayer();

    public ObjectProperty<Player> currentPlayerProperty();

    public boolean setCheatMode(boolean isEnabled);

//    public boolean loadGame(String srcPath);

    public boolean saveGame();

    public BlockStatus getBlockStatus(int row, int col);

    public void sortByTime();

    public void sortByPlayer();

    public void sortByName();

    public boolean save();

    public boolean saveTo(String srcPath);

}
