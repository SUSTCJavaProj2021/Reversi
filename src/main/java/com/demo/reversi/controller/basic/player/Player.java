package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.game.Game;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public abstract class Player {
    public static class GameCounter {
        private int toHuman, toAI;
        private Map<Long, Integer> times;

        public GameCounter() {
            times = new HashMap<>();
        }

        protected void addGame(Player player) {
            if (player.isHuman) {
                ++ toHuman;
            } else {
                ++ toAI;
            }

            times.merge(player.pid.getValue(), 1, (key, value) -> value + 1);
        }

        public int humanCount() {
            return toHuman;
        }

        public int AICount() {
            return toAI;
        }

        public int overallCount() {
            return toHuman + toAI;
        }

        public int playerCount(int pid) {
            return times.getOrDefault(pid, 0);
        }

        @Override
        public String toString() {
            return "[ToHuman = " + toHuman + " ToAI = " + toAI + "Times = " + times.toString().replace("=", " = ") + "]";
        }

        public String print() {
            StringBuilder string = new StringBuilder();

            string.append(String.format("%d %d\n", toHuman, toAI)).append(times.size()).append('\n');

            for (Map.Entry<Long, Integer> entry: times.entrySet()) {
                string.append(String.format("%d %d\n", entry.getKey(), entry.getValue()));
            }

            return string.toString();
        }

        public void load(Scanner scanner) {
            toHuman = scanner.nextInt();
            toAI = scanner.nextInt();

            int size = scanner.nextInt();

            for (int i = 0; i < size; ++i) {
                long key = scanner.nextInt();
                int value = scanner.nextInt();

                times.put(key, value);
            }
        }
    }

    protected static long playerCnt;
    protected LongProperty pid;
    protected boolean isHuman;
    protected StringProperty name;
    protected GameCounter win;
    protected GameCounter lose;
    protected GameCounter total;
    protected IntegerProperty rating;

    public Player(String name) {
        pid = new SimpleLongProperty(++playerCnt);
        this.name = new SimpleStringProperty(name);
        win = new GameCounter();
        lose = new GameCounter();
        total = new GameCounter();
        rating = new SimpleIntegerProperty(1500);
    }

    public static long getPlayerCnt() {
        return playerCnt;
    }

    public static void setPlayerCnt(int playerCnt) {
        Player.playerCnt = playerCnt;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public long getPid() {
        return pid.getValue();
    }

    public LongProperty getPidProperty() {
        return pid;
    }

    public String getName() {
        return name.getValue();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public int getRating() {
        return rating.getValue();
    }

    public IntegerProperty getRatingProperty() {
        return rating;
    }

    public abstract void setName(String name);

    public abstract GameCounter winCounter();

    public abstract GameCounter loseCounter();

    public abstract GameCounter totalCounter();

    public abstract void addWinGame(Player player);

    public abstract void addLoseGame(Player player);

    public abstract void addDrawGame(Player player);

    public abstract double calculateWinRateOverall();

    public abstract double calculateWinRateToHuman();

    public abstract double calculateWinRateToAI();

    public abstract double calculateWinRate(int pid);

    public abstract String print();

    public abstract void load(Scanner scanner);

    public abstract int[] nextStep(Game game);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isHuman && player.isHuman && pid == player.pid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }
}
