package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.controller.basic.player.HumanPlayer;
import com.demo.reversi.controller.basic.player.Player;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GameSystemController extends GameSystem implements GameSystemLayer {
    public static final String SAVE_PATH = "SettingsSave.sav";

    public GameSystemController() {
        super();

        load(new File(SaveLoader.getResource(SAVE_PATH).getAbsolutePath()));
    }

    @Override
    public List<GameControllerLayer> queryPlayerGames(PlayerLayer player) {
        return queryGameControllerAllSorted()
            .stream()
            .filter((g) -> g.getPlayer1().pidProperty().equals(player.pidProperty()) ||
                g.getPlayer2().pidProperty().equals(player.pidProperty()))
            .collect(Collectors.toList());
    }

    @Override
    public PlayerLayer createNewPlayer(String playerName) {
        Player newPlayer = new HumanPlayer(playerName);

        playerList.add(newPlayer);

        return new HumanPlayerController(newPlayer);
    }

    @Override
    public PlayerLayer getPlayer(String playerName) {
        for (Player player: playerList) {
            if (player.getName().equals(playerName)) {
                return new HumanPlayerController(player);
            }
        }

        return null;
    }

    @Override
    public boolean delPlayer(PlayerLayer playerLayer) {
        for (Player player: playerList) {
            if (player.getName().equals(playerLayer.nameProperty().getValue())) {
                playerList.remove(player);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delPlayer(String playerName) {
        for (Player player: playerList) {
            if (player.getName().equals(playerName)) {
                playerList.remove(player);

                return true;
            }
        }
        return false;
    }

    @Override
    public GameController newSimpleGame(Player[] player) {
        return newSimpleGame(player, new boolean[] {false, false}, "Game", initialBoard);
    }

    public GameController newSimpleGame(Player[] player, Board board) {
        return newSimpleGame(player, new boolean[] {false, false}, "Game", board);
    }

    @Override
    public GameController newSimpleGame(Player[] player, boolean[] cheatMode, String name, Board board) {
        for (int i = 0; i < 2; i++) {
            if (player[i].isHuman() && !playerList.contains(player[i])) {
                playerList.add(player[i]);
            }
        }

        return new GameController(player, new ChessColor[] {ChessColor.BLACK, ChessColor.WHITE}, cheatMode, name, board);
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1, String playerName2) {
        PlayerLayer player1 = getPlayer(playerName1);

        if (player1 == null) {
            player1 = createNewPlayer(playerName1);
        }

        PlayerLayer player2 = getPlayer(playerName2);

        if (player2 == null) {
            player2 = createNewPlayer(playerName2);
        }

        return newSimpleGame(new Player[] {player1.get(), player2.get()});
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1, String playerName2, int rowSize, int colSize) {
        Board board = new Board(initialBoard);

        board.setSize(rowSize, colSize);

        PlayerLayer player1 = getPlayer(playerName1);

        if (player1 == null) {
            player1 = createNewPlayer(playerName1);
        }

        PlayerLayer player2 = getPlayer(playerName2);

        if (player2 == null) {
            player2 = createNewPlayer(playerName2);
        }

        return newSimpleGame(new Player[] {player1.get(), player2.get()}, board);
    }

    @Override
    public GameController loadGame(File file) {
        try {
            Scanner scanner = new Scanner(file);
            GameController game = new GameController(scanner, playerList);

            scanner.close();

            return game;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<PlayerLayer> queryPlayerInfoAllSorted() {
        return playerList
            .stream()
            .sorted(Comparator.comparingInt(Player::getRating).reversed())
            .map(HumanPlayerController::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<GameControllerLayer> queryGameControllerAllSorted() {
        File[] gameFiles = SaveLoader.getDirectory(GameController.SAVE_PATH).listFiles();
        List<GameControllerLayer> list = new ArrayList<>();

        assert gameFiles != null;

        for (File file: gameFiles) {
            try {
                Scanner scanner = new Scanner(file);

                list.add(new GameController(scanner, playerList));

                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return list
            .stream()
            .sorted((g1, g2) -> -g1.getGameLastModifiedTime().compareTo(g2.getGameLastModifiedTime()))
            .collect(Collectors.toList());
    }

    @Override
    public GameControllerLayer registerGamePlayable(GameControllerLayer controller) {
        controller.setReadOnly(false);

        return controller;
    }

    @Override
    public GameControllerLayer unregisterGamePlayable(GameControllerLayer controller) {
        controller.setReadOnly(true);

        return controller;
    }

    @Override
    public boolean save() {
        return saveTo(new File(SaveLoader.getResource(SAVE_PATH).getAbsolutePath()));
    }

    @Override
    public void reset() {
        backToDefaultBoard();
        playerList.clear();
    }
}
