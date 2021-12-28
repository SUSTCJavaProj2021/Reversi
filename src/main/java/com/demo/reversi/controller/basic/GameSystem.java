package com.demo.reversi.controller.basic;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.controller.basic.player.HumanPlayer;
import com.demo.reversi.controller.basic.player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class GameSystem {
    protected List<Player> playerList;
    protected Board initialBoard;

    public GameSystem() {
        playerList = new ArrayList<>();
        Player.setPlayerCnt(0);
        backToDefaultBoard();
    }

    public boolean saveTo(File file) {
        try {
            Formatter formatter = new Formatter(file);

            formatter.format("%d\n", playerList.size());

            for (Player player: playerList) {
                formatter.format(player.print());
            }

            formatter.format(initialBoard.print());
            formatter.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean load(File file) {
        try {
            Scanner scanner = new Scanner(file);

            if (!scanner.hasNextInt()) {
                scanner.close();

                return false;
            }

            int playerCnt = scanner.nextInt();

            Player.setPlayerCnt(0);

            for (int i = 0; i < playerCnt; ++i) {
                playerList.add(new HumanPlayer(scanner));
            }

            initialBoard = new Board(scanner);
            scanner.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;
        }
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setInitialBoard(Board initialBoard) {
        this.initialBoard = initialBoard;
    }

    public Game newSimpleGame(Player[] player) {
        return newSimpleGame(player, new boolean[] {false, false}, "Game");
    }

    public Game newSimpleGame(Player[] player, boolean[] cheatMode, String name) {
        return newSimpleGame(player, cheatMode, name, initialBoard);
    }

    public Game newSimpleGame(Player[] player, boolean[] cheatMode, String name, Board board) {
        for (int i = 0; i < 2; i++) {
            if (player[i].isHuman() && !playerList.contains(player[i])) {
                playerList.add(player[i]);
            }
        }

        return new Game(player, new ChessColor[] {ChessColor.BLACK, ChessColor.WHITE}, cheatMode, name, board);
    }

    public Game loadSimpleGame(File file) {
        try {
            Scanner scanner = new Scanner(file);
            Game game = new Game(scanner, playerList);

            scanner.close();

            return game;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    public void backToDefaultBoard() {
        initialBoard = new Board(
            8,
            8,
            new ArrayList<>(List.of(
                new Chess(ChessColor.BLACK, 3, 3),
                new Chess(ChessColor.WHITE, 3, 4),
                new Chess(ChessColor.WHITE, 4, 3),
                new Chess(ChessColor.BLACK, 4, 4))),
            new ArrayList<>());
    }
}
