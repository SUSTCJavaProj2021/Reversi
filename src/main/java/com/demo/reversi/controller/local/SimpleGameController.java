package com.demo.reversi.controller.local;

import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GridStatus;
import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.controller.local.CLIGameController;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.gamepages.GamePageLocal;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SimpleGameController implements GameControllerLayer {
    public boolean isGameModifiable;

    public final PlayerLayer player1;
    public final PlayerLayer player2;
    public PlayerLayer currentPlayer;
    public ObjectProperty<PlayerLayer> currentPlayerProperty;

    public CLIGameController controller;

    public Updatable gamePage;

    public boolean isCheatMode;

    public SimpleGameController(PlayerLayer player1, PlayerLayer player2, boolean isGameModifiable) {
        this.isGameModifiable = isGameModifiable;
        this.player1 = player1;
        this.player2 = player2;

        //TEST ONLY
        controller = new CLIGameController(8,8);

        isCheatMode = false;
        currentPlayerProperty = new SimpleObjectProperty<>(player1);
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
        Log0j.writeInfo(
                String.format("%s Clicked Grid (%d, %d)", controller.currentPlayer.toString(), rowIndex, colIndex));

        controller.makeMove(rowIndex, colIndex);
        updateCurrentPlayer();
        forceSourcedGUIUpdate(rowIndex, colIndex);

        return true;
    }
    @Override
    public void restartGame(){
        controller = new CLIGameController(8,8);
    }

    public void updateCurrentPlayer() {
        switch (controller.getCurrentPlayer()) {
            case WHITE_PLAYER:
                currentPlayer = player2;
                break;
            case BLACK_PLAYER:
                currentPlayer = player1;
                break;
        }
        currentPlayerProperty().setValue(currentPlayer);
    }

    public GridStatus getGridStatus(int rowIndex, int colIndex) {
        return controller.getBlockStatus(rowIndex, colIndex);
    }

    @Override
    public PlayerLayer getPlayer1() {
        return player1;
    }

    @Override
    public PlayerLayer getPlayer2() {
        return player2;
    }

    @Override
    public PlayerLayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public ObjectProperty<PlayerLayer> currentPlayerProperty(){
        return currentPlayerProperty;
    }


    @Override
    public boolean setCheatMode(boolean isEnabled) {
        isCheatMode = isEnabled;
        Log0j.writeInfo("Cheat mode switched: " + (isCheatMode ? "ON" : "OFF"));
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
            Log0j.writeInfo("Cannot update because GUI pointer is null.");
        }
    }

    @Override
    public void forceSourcedGUIUpdate(int row, int col){
        if(gamePage !=null){
            ((GamePageLocal)gamePage).sourcedUpdate(row, col);
        }
        else{
            Log0j.writeInfo("Cannot update because GUI pointer is null.");
        }
    }

}
