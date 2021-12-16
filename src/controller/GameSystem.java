package controller;

import controller.logger.Log0j;

import java.util.ArrayList;
import java.util.List;

public class GameSystem {
    public GameSystem() {

    }

    public void queryPlayerInfo(Player player) {

    }

    public void addPlayer() {

    }

    public void delPlayer() {

    }

    public void getPlayerInfo() {

    }

    public SingleGameController startNewGame() {
        return new SingleGameController(new Player("WHITE PLAYER 114"), new Player("BLACK PLAYER 514"), true);
    }

    public SingleGameController loadGame(int index, boolean isReadOnly) {
        SingleGameController singleGameController = new SingleGameController(new Player("LOAD TEST 1"), new Player("LOAD TEST 2"), !isReadOnly);
        Log0j.writeLog("New GameController Created: " + singleGameController);
        return singleGameController;
    }

    public void updateRanking() {

    }

    public List<SingleGameController> getAllExistingGames() {
        ArrayList<SingleGameController> gameList = new ArrayList<SingleGameController>(0);
        for (int i = 0; i < 10; i++) {
            gameList.add(new SingleGameController(new Player("TEST PLAYER " + i * 2), new Player("TEST PLAYER" + (i * 2 + 1)), false));
        }
        return gameList;
    }

    public void save(){
        saveTo("/save/DefaultSave.save");
    }

    public void saveTo(String srcPath){

    }

}
