package com.demo.reversi.controller;

import com.demo.reversi.view.Updatable;
import javafx.beans.property.ObjectProperty;

public interface GameControllerLayer {

    public boolean bindToGamePage(Updatable gamePage);

//    public boolean bindToChessBoard();

    public int getRowSize();

    public int getColSize();

    public boolean onGridClick(int row, int col);

    public void forceGUIUpdate();

    public void forceSourcedGUIUpdate(int row, int col);

    public PlayerLayer getWhitePlayer();

    public PlayerLayer getBlackPlayer();

    public PlayerLayer getCurrentPlayer();

    public ObjectProperty<PlayerLayer> currentPlayerProperty();

    public boolean setCheatMode(boolean isEnabled);

//    public boolean loadGame(String srcPath);

    public boolean saveGame();

    public GridStatus getGridStatus(int row, int col);

    public boolean save();

    public boolean saveTo(String srcPath);

}
