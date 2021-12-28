package com.demo.reversi.controller.basic.game;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Step {
    private Player player;
    private boolean isPause;
    private Chess chess;
    private List<Chess[]> modifiedChessList;

    public Step(Player player) {
        this.player = player;
        this.isPause = true;
        this.chess = null;
        this.modifiedChessList = new ArrayList<>();
    }

    public Step(Player player, Chess chess, List<Chess[]> chessList) {
        this.player = player;
        this.isPause = false;
        this.chess = chess;
        this.modifiedChessList = chessList;
    }

    public Step(Scanner scanner) {
        modifiedChessList = new ArrayList<>();
        load(scanner);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPause() {
        return isPause;
    }

    public Chess getChess() {
        return chess;
    }

    public List<Chess[]> getModifiedChessList() {
        return modifiedChessList;
    }

    public String print() {
        StringBuilder string = new StringBuilder();

        string.append(isPause).append('\n');

        if (chess != null) {
            string.append(chess.print());
        }

        string.append(modifiedChessList.size()).append('\n');

        for (Chess[] pair: modifiedChessList) {
            string.append(pair[0].print()).append(pair[1].print());
        }

        return string.toString();
    }

    public void load(Scanner scanner) {
        isPause = scanner.nextBoolean();

        if (!isPause) {
            chess = new Chess(scanner);
        }

        int size = scanner.nextInt();

        for (int i = 0; i < size; i++) {
            Chess chess1 = new Chess(scanner), chess2 = new Chess(scanner);

            modifiedChessList.add(new Chess[] {chess1, chess2});
        }
    }
}
