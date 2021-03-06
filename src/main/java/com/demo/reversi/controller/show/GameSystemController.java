package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.GameSystem;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.player.AIPlayer;
import com.demo.reversi.controller.basic.player.HumanPlayer;
import com.demo.reversi.controller.basic.player.Player;
import com.demo.reversi.controller.basic.player.Mode;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameEditor;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.save.SaveLoader;

import java.io.File;
import java.io.FileNotFoundException;
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
        PlayerLayer previousPlayer = getPlayer(playerName);

        if (previousPlayer != null) {
            Log0j.writeInfo("You create a existed player named " + playerName);

            return previousPlayer;
        }

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
        PlayerLayer player1 = createNewPlayer(playerName1);
        PlayerLayer player2 = createNewPlayer(playerName2);

        return newSimpleGame(new Player[] {player1.get(), player2.get()});
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1, String playerName2, int rowSize, int colSize) {
        Board board = new Board(initialBoard);

        board.setSize(rowSize, colSize);

        PlayerLayer player1 = createNewPlayer(playerName1);
        PlayerLayer player2 = createNewPlayer(playerName2);

        return newSimpleGame(new Player[] {player1.get(), player2.get()}, board);
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1,
                                            boolean isAIEnabled1,
                                            Mode mode1,
                                            String playerName2,
                                            boolean isAIEnabled2,
                                            Mode mode2,
                                            int rowSize,
                                            int colSize) {
        Board board = new Board();

        board.setSize(rowSize, colSize);

        PlayerLayer player1 = isAIEnabled1 ? new AIPlayerController(mode1.getPlayer()) : createNewPlayer(playerName1);
        PlayerLayer player2 = isAIEnabled2 ? new AIPlayerController(mode2.getPlayer()) :createNewPlayer(playerName2);

        return newSimpleGame(new Player[] {player1.get(), player2.get()}, board);
    }

    @Override
    public GameControllerLayer startNewGame(String playerName1, boolean isAIEnabled1, Mode mode1, String playerName2, boolean isAIEnabled2, Mode mode2, boolean isEditorApplied) {
        Board board = new Board(initialBoard);

        PlayerLayer player1 = isAIEnabled1 ? new AIPlayerController(mode1.getPlayer()) : createNewPlayer(playerName1);
        PlayerLayer player2 = isAIEnabled2 ? new AIPlayerController(mode2.getPlayer()) :createNewPlayer(playerName2);

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
    public GameEditor getGameEditor() {
        return new GameEditorController();
    }

    @Override
    public boolean save() {
        Log0j.writeCaution("GameSystem saved.");
        return saveTo(new File(SaveLoader.getResource(SAVE_PATH).getAbsolutePath()));
    }

    @Override
    public void reset() {
        backToDefaultBoard();
        playerList.clear();
    }

    @Override
    public void saveConfig(GameEditor gameEditor) {
        this.initialBoard = gameEditor.saveConfig();
    }
}
