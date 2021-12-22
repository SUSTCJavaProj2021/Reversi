package com.demo.reversi.controller.local;

import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.controller.GameSystemLayer;
import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.logger.Log0j;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameSystem implements GameSystemLayer {
    public SimpleGameSystem() {

    }

    @Override
    public void queryPlayerGames(PlayerLayer player) {

    }

    @Override
    public boolean addPlayer() {
        return true;
    }

    @Override
    public boolean delPlayer() {
        return true;
    }

    @Override
    public SimpleGameController startNewGame(String playerName1, String playerName2) {
        return new SimpleGameController(new SimplePlayer(playerName1), new SimplePlayer(playerName2), true);
    }

    @Override
    public GameControllerLayer loadGame(int index, boolean isModifiable) {
        SimpleGameController simpleGameController = new SimpleGameController(new SimplePlayer("LOAD TEST 1"), new SimplePlayer("LOAD TEST 2"), isModifiable);
        Log0j.writeInfo("loadGame method test: New GameController Created: " + simpleGameController);
        return simpleGameController;
    }

    @Override
    public boolean updateRanking() {
        return true;
    }

    @Override
    public List<PlayerLayer> queryPlayerInfoAllSorted() {
        ArrayList<PlayerLayer> playerList = new ArrayList<PlayerLayer>();
        //todo: Change test method
        playerList.add(new SimplePlayer("AQX0783"));
        playerList.add(new SimplePlayer("B7820HQ"));
        playerList.add(new SimplePlayer("S9980HK"));
        playerList.add(new SimplePlayer("W1165G7"));
        playerList.add(new SimplePlayer("B1135G7"));
        playerList.add(new SimplePlayer("N8650UK"));
        playerList.add(new SimplePlayer("XTQ2014"));
        playerList.add(new SimplePlayer("Test Player wdnmd"));
        playerList.add(new SimplePlayer("我带你们打！"));
        playerList.add(new SimplePlayer("dnmd"));
        playerList.add(new SimplePlayer("Raiden Shogun"));
        playerList.add(new SimplePlayer("Zachary"));
        return playerList;
    }

    @Override
    public List<GameControllerLayer> getAllExistingGames() {
        ArrayList<GameControllerLayer> gameList = new ArrayList<GameControllerLayer>(0);
        for (int i = 0; i < 10; i++) {
            gameList.add(new SimpleGameController(new SimplePlayer("TEST PLAYER " + i * 2), new SimplePlayer("TEST PLAYER" + (i * 2 + 1)), false));
        }
        return gameList;
    }

    @Override
    public boolean save() {
        return saveTo("/save/DefaultSave.save");
    }

    @Override
    public boolean saveTo(String srcPath) {
        return true;
    }

}
