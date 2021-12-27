package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NormalAIPlayer extends AIPlayer {
    public NormalAIPlayer() {
        super("_AI_NORMAL");

        this.mode = Mode.NORMAL;
        --playerCnt;
        pid.setValue(AI_PID_OFFSET + 1);
    }

    @Override
    public int[] nextStep(Game game) {
        Board board = game.getBoard();
        List<int[]> moves = game.getPossibleMoves();
        int[] value = new int[moves.size()];

        for (int i = 0; i < moves.size(); i++) {
            int[] move = moves.get(i);
            Chess chess = new Chess(game.getColor(), move[0], move[1]);

            value[i] = board.checkPosition(chess, game.isCheatMode()).size() + borderWeight(move, game.getBoard());
        }

        int max = Collections.max(Arrays.stream(value).boxed().collect(Collectors.toList()));
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < moves.size(); i++) {
            if (value[i] == max) {
                result.add(moves.get(i));
            }
        }

        return result.get(random.nextInt(result.size()));
    }
}
