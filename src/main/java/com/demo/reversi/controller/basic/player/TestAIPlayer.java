package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;

import java.util.List;

public class TestAIPlayer extends AIPlayer {
    public TestAIPlayer() {
        super("_AI_TEST");

        this.mode = Mode.TEST;
        --playerCnt;
        pid.setValue(AI_PID_OFFSET + 114514);
    }

    private static final int RANDOM_TIMES = 100;

    private boolean MonteCarlo(Chess chess, Board initialBoard) {
        Board board = new Board(initialBoard);

        board.addChess(chess, false);

        ChessColor color = ChessColor.dual(chess.getColor());

        for (int pauseCnt = 0; pauseCnt < 2; color = ChessColor.dual(color)) {
            List<int[]> list = board.showAllPossibleMoves(color, false);

            if (list.isEmpty()) {
                ++pauseCnt;
                continue;
            } else {
                pauseCnt = 0;
            }

            int[] move = list.get(random.nextInt(list.size()));

            board.addChess(new Chess(color, move[0], move[1]), false);
        }

        return board.calculateWinner() == chess.getColor();
    }

    @Override
    public int[] nextStep(Game game) {
        Board board = game.getBoard();
        List<int[]> moves = game.getPossibleMoves();
        int[] result = new int[2];
        int maxWinTimes = -1;

        for (int[] move: moves) {
            int winTimes = 0;

            for (int i = 0; i < RANDOM_TIMES; i++) {
                if (MonteCarlo(new Chess(game.getColor(), move[0], move[1]), board)) {
                    ++winTimes;
                }
            }

            if (winTimes > maxWinTimes) {
                maxWinTimes = winTimes;
                result = move;
            }
        }

        return result;
    }
}
