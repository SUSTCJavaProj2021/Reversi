package com.demo.reversi.controller;

import java.util.List;

public interface GameSystemLayer {

    //todo: return type should be ArrayList<Game>
    public void queryPlayerGames(PlayerLayer player);

    public boolean addPlayer();

    public boolean delPlayer();

    public GameControllerLayer startNewGame();

    public GameControllerLayer loadGame(int index, boolean isReadOnly);

    public boolean updateRanking();

    public List<GameControllerLayer> getAllExistingGames();

    public boolean save();

    public boolean saveTo(String srcPath);
}
