package com.demo.reversi.controller;

import java.util.List;

public interface GameSystem {

    //todo: return type should be ArrayList<Game>
    public void queryPlayerGames(Player player);

    public boolean addPlayer();

    public boolean delPlayer();

    public GameController startNewGame();

    public GameController loadGame(int index, boolean isReadOnly);

    public boolean updateRanking();

    public List<GameController> getAllExistingGames();

    public boolean save();

    public boolean saveTo(String srcPath);
}
