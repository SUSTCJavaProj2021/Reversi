package com.demo.reversi.controller;

import java.util.ArrayList;
import java.util.List;

public interface GameSystemLayer {

    //todo: return type should be ArrayList<Game>
    public void queryPlayerGames(PlayerLayer player);

    public boolean addPlayer();

    public boolean delPlayer();

    public GameControllerLayer startNewGame(String playerName1, String playerName2);

    public GameControllerLayer loadGame(int index, boolean isGameModifiable);

    public boolean updateRanking();

    /**
     *
     * @return List of players in sorted order (according to MMR)
     */
    public List<PlayerLayer> queryPlayerInfoAllSorted();

    public List<GameControllerLayer> getAllExistingGames();

    public boolean save();

    public boolean saveTo(String srcPath);
}
