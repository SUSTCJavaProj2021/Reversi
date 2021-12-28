package com.demo.reversi.controller.local;

import com.demo.reversi.controller.interfaces.Difficulty;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SimpleGameSystem implements GameSystemLayer {
    public SimpleGameSystem() {

    }

    @Override
    public List<GameControllerLayer> queryPlayerGames(PlayerLayer player) {
        return null;
    }

    @Override
    public PlayerLayer createNewPlayer(String playerName) {
        return new SimplePlayer(playerName);
    }

    @Override
    public PlayerLayer getPlayer(String playerName) {
        return new SimplePlayer(playerName);
    }

    @Override
    public boolean delPlayer(PlayerLayer player) {
        return true;
    }

    @Override
    public boolean delPlayer(String playerName) {
        return true;
    }

    @Override
    public GameControllerLayer startNewGame(String player1, String player2) {
        return startNewGame(player1, player2, 8, 8);
    }

    @Override
    public GameControllerLayer startNewGame(String player1, String player2, int rowSize, int colSize) {
        return new SimpleGameController(getPlayer(player1), getPlayer(player2), rowSize, colSize, true);
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1, boolean isAIEnabled1, Difficulty difficulty1, String playerName2, boolean isAIEnabled2, Difficulty difficulty2, int rowSize, int colSize) {
        return null;
    }

    @Override
    public GameControllerLayer loadGame(File file) {
        return new SimpleGameController(new SimplePlayer("Loaded Player"), new SimplePlayer("Loaded Player"), true);
    }

    @Override
    public GameControllerLayer registerGamePlayable(GameControllerLayer controller) {
        if (controller == null) {
            SimpleGameController simpleGameController = new SimpleGameController(new SimplePlayer("LOAD TEST 1"), new SimplePlayer("LOAD TEST 2"), true);
            Log0j.writeInfo("loadGame method test: New GameController Created: " + simpleGameController);
            return simpleGameController;
        } else {
            //todo: you should set the controller to be playable
            return controller;
        }
    }

    @Override
    public GameControllerLayer unregisterGamePlayable(GameControllerLayer controller) {
        //todo: you should set the controller to be unplayable
        return controller;
    }

    @Override
    public boolean load(File file) {
        return false;
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
    public List<GameControllerLayer> queryGameControllerAllSorted() {
        ArrayList<GameControllerLayer> gameList = new ArrayList<GameControllerLayer>(0);
        for (int i = 0; i < 10; i++) {
            gameList.add(new SimpleGameController(new SimplePlayer("TEST PLAYER " + i * 2), new SimplePlayer("TEST PLAYER" + (i * 2 + 1)), false));
        }
        return gameList;
    }

    @Override
    public boolean save() {
        try {
            return saveTo(new File(SaveLoader.class.getResource("DefaultSave.sav").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeInfo("Failed to save.");
            return false;
        }
    }

    @Override
    public boolean saveTo(File file) {
        return true;
    }

    @Override
    public void reset() {
    }

}
