package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.game.Game;
import com.demo.reversi.logger.Log0j;

import java.util.Scanner;

public class HumanPlayer extends Player {
    protected static final int K_FACTOR = 32;

    public HumanPlayer(String name) {
        super(name);

        isHuman = true;
    }

    public HumanPlayer(Scanner scanner) {
        super("");

        isHuman = true;
        load(scanner);
    }

    @Override
    public void setName(String name) {
        this.name.setValue(name);
    }

    @Override
    public GameCounter winCounter() {
        return win;
    }

    @Override
    public GameCounter loseCounter() {
        return lose;
    }

    @Override
    public GameCounter totalCounter() {
        return total;
    }

    private int updateRating(int rating1, int rating2, double point) {
        double q1 = Math.pow(10.0, rating1 / 400.0), q2 = Math.pow(10.0, rating2 / 400.0);

        return (int)Math.round(rating1 + K_FACTOR * (point - q1 / (q1 + q2)));
    }

    @Override
    public void addWinGame(Player player) {
        win.addGame(player);
        total.addGame(player);

        rating.setValue(updateRating(rating.getValue(), player.rating.getValue(), 1.0));
    }

    @Override
    public void addLoseGame(Player player) {
        lose.addGame(player);
        total.addGame(player);

        rating.setValue(updateRating(rating.getValue(), player.rating.getValue(), 0.0));
    }

    @Override
    public void addDrawGame(Player player) {
        total.addGame(player);

        rating.setValue(updateRating(rating.getValue(), player.rating.getValue(), 0.5));
    }

    @Override
    public double calculateWinRateToHuman() {
        if (total.humanCount() == 0) {
            return -1.0;
        }

        return 1.0 * win.humanCount() / total.humanCount();
    }

    @Override
    public double calculateWinRateToAI() {
        if (total.AICount() == 0) {
            return -1.0;
        }

        return 1.0 * win.AICount() / total.AICount();
    }

    @Override
    public double calculateWinRateOverall() {
        if (total.overallCount() == 0) {
            return -1.0;
        }

        return 1.0 * win.overallCount() / total.overallCount();
    }

    @Override
    public double calculateWinRate(int pid) {
        if (total.playerCount(pid) == 0) {
            return -1.0;
        }

        return 1.0 * win.playerCount(pid) / total.playerCount(pid);
    }

    @Override
    public String toString() {
        return "[ID = " + pid + " Name = " + name + " WinMap = " + win + " LoseMap = " + lose + " TotalMap = " + total + "]";
    }

    @Override
    public String print() {
        return pid.getValue() + "\n" + name.getValue() + "\n" + win.print() + lose.print() + total.print();
    }

    @Override
    public void load(Scanner scanner) {
        pid.setValue(scanner.nextLong());
        name.setValue(scanner.nextLine());
        win.load(scanner);
        lose.load(scanner);
        total.load(scanner);
    }

    @Override
    public int[] nextStep(Game game) {
        assert isHuman;

        return new int[] {-1, -1};
    }
}
