package com.demo.reversi.controller;

import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePageLocal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SimpleGameController implements GameController{
    public boolean isGameModifiable;

    public final Player whitePlayer;
    public final Player blackPlayer;
    public Player currentPlayer;
    public ObjectProperty<Player> currentPlayerProperty;

    public CLIGameController controller;

    public Updatable gamePage;

    public boolean isCheatMode;

    public SimpleGameController(Player blackPlayer, Player whitePlayer, boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;

        //TEST ONLY
        controller = new CLIGameController(8,8);

        isCheatMode = false;
        currentPlayerProperty = new SimpleObjectProperty<>(blackPlayer);
        updateCurrentPlayer();
    }

    @Override
    public int getRowSize() {
        return controller.rowSize;
    }

    @Override
    public int getColSize() {
        return controller.colSize;
    }

    public boolean getModifiability() {
        return this.isGameModifiable;
    }

    @Override
    public boolean onGridClick(int rowIndex, int colIndex) {
        Log0j.writeLog(
                String.format("%s Clicked Grid (%d, %d)", controller.currentPlayer.toString(), rowIndex, colIndex));

        controller.makeMove(rowIndex, colIndex);
        updateCurrentPlayer();
        forceSourcedGUIUpdate(rowIndex, colIndex);

        return true;
    }

    public void updateCurrentPlayer() {
        switch (controller.getCurrentPlayer()) {
            case WHITE_PLAYER:
                currentPlayer = whitePlayer;
                break;
            case BLACK_PLAYER:
                currentPlayer = blackPlayer;
                break;
        }
        currentPlayerProperty().setValue(currentPlayer);
    }

    public GridStatus getGridStatus(int rowIndex, int colIndex) {
        return controller.getBlockStatus(rowIndex, colIndex);
    }

    @Override
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    @Override
    public Player getBlackPlayer() {
        return blackPlayer;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public ObjectProperty<Player> currentPlayerProperty(){
        return currentPlayerProperty;
    }


    @Override
    public boolean setCheatMode(boolean isEnabled) {
        isCheatMode = isEnabled;
        Log0j.writeLog("Cheat mode switched: " + (isCheatMode ? "ON" : "OFF"));
        return true;
    }

    @Override
    public boolean saveGame() {
        return true;
    }

    ;

    @Override
    public boolean save(){
        return saveTo("/save/DefaultSave.save");
    }

    @Override
    public boolean saveTo(String srcPath) {
        return true;
    }

    ;

    @Override
    public boolean bindToGamePage(Updatable gamePage){
        this.gamePage = gamePage;
        return true;
    }

    @Override
    public void forceGUIUpdate() {

        if(gamePage !=null){
            gamePage.update();
        }
        else{
            Log0j.writeLog("Cannot update because GUI pointer is null.");
        }
    }

    @Override
    public void forceSourcedGUIUpdate(int row, int col){
        if(gamePage !=null){
            ((GamePageLocal)gamePage).sourcedUpdate(row, col);
        }
        else{
            Log0j.writeLog("Cannot update because GUI pointer is null.");
        }
    }

}
