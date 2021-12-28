package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.chess.Chess;
import com.demo.reversi.controller.basic.chess.ChessColor;
import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.interfaces.GameEditor;
import com.demo.reversi.controller.interfaces.GridStatus;
import com.demo.reversi.logger.Log0j;

import java.util.ArrayList;
import java.util.List;

public class GameEditorController extends GameController implements GameEditor {
    public GameEditorController() {
        super(new Board());

        mode = BrushMode.NULL;
    }

    public GameEditorController(int rowSize, int columnSize) {
        super(new Board(rowSize, columnSize, new ArrayList<>(), new ArrayList<>()));

        mode = BrushMode.NULL;
    }

    private enum BrushMode {
        PLAYER_1, PLAYER_2, UNOCCUPIED, BANNED, NULL;
    }

    private BrushMode mode;

    @Override
    public void setBrushAsPlayer1() {
        mode = BrushMode.PLAYER_1;
    }

    @Override
    public void setBrushAsPlayer2() {
        mode = BrushMode.PLAYER_2;
    }

    @Override
    public void setBrushAsEmptying() {
        mode = BrushMode.UNOCCUPIED;
    }

    @Override
    public void setBrushAsBanning() {
        mode = BrushMode.BANNED;
    }

    @Override
    public void setBrushAsNull() {
        mode = BrushMode.NULL;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void resetConfig() {
        board = new Board();
        mode = BrushMode.NULL;
    }

    @Override
    public void onGridClick(int row, int col) {
        if (!board.isValid(row, col)) {
            Log0j.writeInfo(
                String.format("Invalid Move (%d, %d): The position is invalid (out of board or banned)", row, col));

            return;
        }

        switch (mode) {
            case PLAYER_1 -> {
                board.getChess()[row][col] = new Chess(ChessColor.BLACK, row, col);
                board.setBanned(row, col, false);
            }

            case PLAYER_2 -> {
                board.getChess()[row][col] = new Chess(ChessColor.WHITE, row, col);
                board.setBanned(row, col, false);
            }

            case UNOCCUPIED -> {
                board.getChess()[row][col] = new Chess(row, col);
                board.setBanned(row, col, false);
            }

            case BANNED -> {
                board.getChess()[row][col] = null;
                board.setBanned(row, col, true);
            }
        }

        forceGUIUpdate();
    }

    @Override
    public GridStatus getGridStatus(int row, int col) {
        if (board.isBanned(row, col)) {
            return GridStatus.BANNED;
        } else {
            Chess chess = board.getChess()[row][col];

            if (chess.getColor() == ChessColor.NULL) {
                return GridStatus.UNOCCUPIED;
            } else if (chess.getColor() == ChessColor.BLACK) {
                return GridStatus.PLAYER_1;
            } else {
                return GridStatus.PLAYER_2;
            }
        }
    }

    @Override
    public void resizeBoard(int rowSize, int colSize) {
        board.forceSetSize(rowSize, colSize);
    }
}
