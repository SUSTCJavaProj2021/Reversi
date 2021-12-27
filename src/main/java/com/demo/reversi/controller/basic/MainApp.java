package com.demo.reversi.controller.basic;

import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.controller.basic.player.HumanPlayer;
import com.demo.reversi.controller.basic.player.Mode;
import com.demo.reversi.controller.basic.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
    }
/*
    public static void test1() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new HumanPlayer("DengQingwen"), player2 = new HumanPlayer("DaShen");

        Game game = GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game");

        while (true) {
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    public static void test2() {
        Scanner scanner = new Scanner(System.in);

        GameSystem.loadSettings(getFile("src/main/resources/settings.sav"));
        Game game = GameSystem.loadGame(getFile("src/main/resources/Game.sav"));

        while (true) {
            assert game != null;
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    public static void test3() {
        Scanner scanner = new Scanner(System.in);
        Player player1 = new HumanPlayer("DengQingwen"), player2 = Mode.EASY.getPlayer();

        Game game = GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game2");

        while (true) {
            if (process(scanner, game)) break;
        }

        GameSystem.saveGame(getFile("src/main/resources/" + game.getName() + ".sav"), game);
        GameSystem.saveSettings(getFile("src/main/resources/settings.sav"));
    }

    public static void test4() {
        Player player1 = Mode.HARD.getPlayer(), player2 = Mode.TEST.getPlayer();
        int win1 = 0, win2 = 0;

        for (int i = 0; i < 50; ++i) {
            Game game = i % 2 == 0 ?
                GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game" + i) :
                GameSystem.newGame(new Player[] {player2, player1}, new boolean[] {false, false}, "Game" + i);
            boolean moveValid = true;

            while (true) {
                if (!moveValid) {
                    if (game.pause()) {
                        Player temp = game.endGame();

                        if (temp == player1) {
                            ++win1;
                            System.out.println("A win");
                        } else {
                            ++win2;
                            System.out.println("B win");
                        }

                        break;
                    } else {
                        moveValid = game.isMovable();
                        continue;
                    }
                }

                int[] next = game.getCurrentPlayer().nextStep(game);

                moveValid = game.move(next[0], next[1]);
                // System.out.printf("Step=(%d,%d)\n", next[0], next[1]);
                // game.getBoard().printToCmd();
                // System.out.println();
                // try {
                //     Thread.sleep(500);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
            }
        }

        System.out.printf("A win %d times, B win %d times", win1, win2);
    }

    public static void test5() {
        Scanner scanner = new Scanner(System.in);
        Player player2 = new HumanPlayer("DengQingwen"), player1 = Mode.HARD.getPlayer();

        Game game = GameSystem.newGame(new Player[] {player1, player2}, new boolean[] {false, false}, "Game");

        while (true) {
            if (process(scanner, game)) break;
        }
    }

    private static boolean process(Scanner scanner, Game game) {
        game.getBoard().printToCmd();

        if (game.getCurrentPlayer().isHuman()) {
            int x = scanner.nextInt(), y = scanner.nextInt();

            if (x == 0) {
                return true;
            } else {
                --x;
                --y;
                game.move(x, y);
            }
            return false;
        } else {
            int[] move = game.getCurrentPlayer().nextStep(game);

            game.move(move[0], move[1]);
            System.out.printf("MOVE: %d %d\n", move[0], move[1]);

            return false;
        }
    }

    public static File getFile(String pathname) {
        File file = new File(pathname);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
 */
}
