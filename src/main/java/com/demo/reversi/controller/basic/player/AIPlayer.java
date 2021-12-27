/*
todo: implement AI
*/

package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.game.Board;

import java.util.Random;
import java.util.Scanner;

public abstract class AIPlayer extends Player {
    protected static final int AI_PID_OFFSET = 1000000000;
    protected static final int MAX_WEIGHT = 50;

    public AIPlayer(String mode) {
        super(mode);

        random = new Random();
        isHuman = false;
    }

    protected Mode mode;
    protected final Random random;

    public static int getOffset() {
        return AI_PID_OFFSET;
    }

    public Mode getMode() {
        return mode;
    }

    protected int borderWeight(int[] position, Board board) {
        int result = 0;

        if (position[0] == 0 || position[0] == board.getRowSize() - 1) {
            ++result;

            if (position[1] == 1 || position[1] == board.getColumnSize() - 2) {
                return -MAX_WEIGHT;
            }
        }

        if (position[1] == 0 || position[1] == board.getColumnSize() - 1) {
            ++result;

            if (position[0] == 1 || position[0] == board.getRowSize() - 2) {
                return -MAX_WEIGHT;
            }
        }

        return result == 2 ? MAX_WEIGHT : 0;
    }

    protected int weightedSelect(double[] weight) {
        final double OFFSET = 10000.0;

        double[] generalized = weight.clone();
        double sum = 0;

        for (int i = 0; i < generalized.length; i++) {
            generalized[i] += OFFSET;
            sum += generalized[i];
        }

        double result = random.nextDouble(sum);

        for (int i = 0; i < generalized.length; i++) {
            if (generalized[i] >= result) {
                return i;
            } else {
                result -= generalized[i];
            }
        }

        return -1;
    }

    @Override
    public void setName(String name) {}

    @Override
    public GameCounter winCounter() {
        return null;
    }

    @Override
    public GameCounter loseCounter() {
        return null;
    }

    @Override
    public GameCounter totalCounter() {
        return null;
    }

    @Override
    public void addWinGame(Player player) {}

    @Override
    public void addLoseGame(Player player) {}

    @Override
    public void addDrawGame(Player player) {}

    @Override
    public double calculateWinRateOverall() {
        return -1.0;
    }

    @Override
    public double calculateWinRateToHuman() {
        return -1.0;
    }

    @Override
    public double calculateWinRateToAI() {
        return -1.0;
    }

    @Override
    public double calculateWinRate(int pid) {
        return -1.0;
    }

    @Override
    public String toString() {
        return "[AI: " + name + "]";
    }

    @Override
    public String print() {
        return "";
    }

    @Override
    public void load(Scanner scanner) {}
}
