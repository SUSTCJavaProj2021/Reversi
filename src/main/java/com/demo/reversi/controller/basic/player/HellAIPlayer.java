package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.basic.game.Game;

import java.util.List;

public class HellAIPlayer extends AIPlayer {
    protected static final int MAX_DEPTH = 6;
    protected static final int END_WEIGHT = 10000000;

    public HellAIPlayer() {
        super("_AI_HELL");

        this.mode = Mode.HELL;
        --playerCnt;
        pid.setValue(AI_PID_OFFSET + 3);
    }

    private static class State {
        public static final int CORNER_WEIGHT = 80;
        public static final int CORNER_NEIGHBOR_WEIGHT = -20;
        public static final int EDGE_WEIGHT = 1;

        public ChessColor color;
        public List<Chess[]> step;
        public Board board;

        public State(ChessColor color, Board board) {
            this.color = color;
            this.board = board;
        }

        public State(ChessColor color, int[] move, Board board) {
            Chess chess = new Chess(color, move[0], move[1]);

            this.color = color;
            step = board.checkPosition(chess, false);
            this.board = board;
        }

        public void into() {
            board.changeInto(step);
        }

        public void back() {
            board.changeBack(step);
        }

        public int getWeight(ChessColor rootColor) {
            int weight = board.showAllPossibleMoves(color, false).size();

            if (color != rootColor) {
                weight = -weight;
            }

            Chess[][] chessBoard = board.getChess();
            int rowSize = board.getRowSize(), columnSize = board.getColumnSize();

            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < columnSize; j++) {
                    if (board.isValid(i, j) && chessBoard[i][j].getColor() != ChessColor.NULL) {
                        int[] position = new int[] {i, j};

                        if (chessBoard[i][j].getColor() == rootColor) {
                            ++weight;

                            if (isCorner(position, board)) {
                                weight += CORNER_WEIGHT;
                            } else if (isDangerous(position, board)) {
                                weight += CORNER_NEIGHBOR_WEIGHT;
                            } else if (isEdge(position, board)) {
                                weight += EDGE_WEIGHT;
                            }
                        } else {
                            --weight;

                            if (isCorner(position, board)) {
                                weight -= CORNER_WEIGHT;
                            } else if (isDangerous(position, board)) {
                                weight -= CORNER_NEIGHBOR_WEIGHT;
                            } else if (isEdge(position, board)) {
                                weight -= EDGE_WEIGHT;
                            }
                        }
                    }
                }
            }

            return weight;
        }
    }

    private int[] bestMove;
    private ChessColor rootColor;

    private int alphaBetaSearch(State s, int depth, int alpha, int beta, boolean isParentPause) {
        List<int[]> list = s.board.showAllPossibleMoves(s.color, false);

        if (list.isEmpty() && isParentPause) {
            return s.board.calculateWinner() == rootColor ? END_WEIGHT : -END_WEIGHT;
        } else if (depth == MAX_DEPTH) {
            return s.getWeight(rootColor);
        } else if (list.isEmpty()) {
            State child = new State(ChessColor.dual(s.color), s.board);
            int score = alphaBetaSearch(child, depth + 1, alpha, beta, true);

            if (depth % 2 == 0) {
                alpha = Math.max(alpha, score);
            } else {
                beta = Math.min(beta, score);
            }
        } else {
            for (int[] move: list) {
                State child = new State(ChessColor.dual(s.color), move, s.board);

                child.into();

                int score = alphaBetaSearch(child, depth + 1, alpha, beta, false);

                child.back();

                if (depth % 2 == 0) {
                    if (depth == 0 && score > alpha) {
                        bestMove = move;
                    }

                    alpha = Math.max(alpha, score);
                } else {
                    beta = Math.min(beta, score);
                }

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return depth % 2 == 0 ? alpha : beta;
    }

    @Override
    public int[] nextStep(Game game) {
        rootColor = game.getColor();
        bestMove = null;

        alphaBetaSearch(new State(rootColor, game.getBoard()), 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

        assert bestMove.length == 2;

        return bestMove;
    }
}
