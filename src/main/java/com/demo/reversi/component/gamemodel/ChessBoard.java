package com.demo.reversi.component.gamemodel;

import com.demo.reversi.controller.GridStatus;
import com.demo.reversi.controller.GameControllerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * You should not in any way directly manipulate the contents of the ChessBoard instance.
 */
public class ChessBoard extends HBox implements Updatable {
    public static final double DEFAULT_BOARD_MIN_SIZE = 400;
    public static final double CHESS_SIZE_RATIO = 0.800;
    public static final long CHESS_REVERSE_GAP_TIME = 70; //Unit: millis

    public final VBox vBoxCover;
    public final GridPane grid;
    public final BoardGridComponent gridBases[][];
    public final int rowSize, colSize;
    public final GameControllerLayer controller;
    public final double cellMinSize;
    public final double boardSize;

    private final Theme theme;

    public ChessBoard(GameControllerLayer controller, Theme theme) {
        this(controller, theme, 0);
    }

    /**
     * New Game Initialization
     *
     * @param controller    the specific GameController
     * @param theme         the specific board theme
     * @param prefBoardSize the preferred board size
     */
    public ChessBoard(GameControllerLayer controller, Theme theme, double prefBoardSize) {
        this.theme = theme;
        this.controller = controller;
        this.rowSize = controller.getRowSize();
        this.colSize = controller.getColSize();
        grid = new GridPane();

        if (prefBoardSize <= 0) {
            boardSize = DEFAULT_BOARD_MIN_SIZE;
        } else {
            boardSize = prefBoardSize;
        }

        cellMinSize = boardSize / this.rowSize;

        gridBases = new BoardGridComponent[rowSize][colSize];

        for (int row = 0; row < this.rowSize; row++) {
            gridBases[row] = new BoardGridComponent[this.colSize];
            for (int col = 0; col < this.colSize; col++) {

                final int tmpRow = row, tmpCol = col;

                //Initialize Grids
                gridBases[row][col] = new BoardGridComponent(theme);
                gridBases[row][col].setMinHeight(cellMinSize);
                gridBases[row][col].setMinWidth(cellMinSize);
                theme.bindToBorderPaint(gridBases[row][col].borderProperty());
                if ((row + col) % 2 == 0) {
                    theme.bindToChessBoardPaint1(gridBases[row][col].backgroundProperty());
                } else {
                    theme.bindToChessBoardPaint2(gridBases[row][col].backgroundProperty());
                }

                grid.add(gridBases[row][col], col, row);

                //Dynamic Chess Size
                Chess chess = new Chess(15, theme, Chess.ChessOwner.PLACEHOLDER);
                chess.playerPaint1PR().setValue(Color.BLACK);
                chess.playerPaint2PR().setValue(Color.WHITE);

                gridBases[row][col].getChildren().add(chess);
                StackPane.setAlignment(chess, Pos.CENTER);

                chess.chessSizePR.bind(Bindings.min(
                        gridBases[row][col].widthProperty().divide(2).multiply(CHESS_SIZE_RATIO),
                        gridBases[row][col].heightProperty().divide(2).multiply(CHESS_SIZE_RATIO)));
            }
        }
        Log0j.writeLog("Grid background color all bound.");

        for (int row = 0; row < rowSize; row++) {
            grid.getRowConstraints().add(new RowConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                    Priority.SOMETIMES, VPos.CENTER, true));
        }
        for (int col = 0; col < colSize; col++) {
            grid.getColumnConstraints().add(new ColumnConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                    Priority.SOMETIMES, HPos.CENTER, true));
        }
        Log0j.writeLog("Grid shape initialized.");

        grid.setMinWidth(boardSize);
        grid.setMinHeight(boardSize);
        grid.setPrefWidth(GridPane.USE_COMPUTED_SIZE);
        grid.setPrefHeight(GridPane.USE_COMPUTED_SIZE);


        //Techniques to make the ChessBoard always square
        vBoxCover = new VBox();
        vBoxCover.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());
        /**
         * Let me explain the strange constant 0.99 here:
         * So when I was developing this chessboard, I found that it will start shaking
         * after resized (if I bind its size properties directly to its parent container)
         * However, adding a 0.99 will prevent this. I don't know why.
         */
        vBoxCover.prefHeightProperty().bind(binding.multiply(0.99));
        vBoxCover.prefWidthProperty().bind(binding.multiply(0.99));

        vBoxCover.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        vBoxCover.setFillWidth(true);

        vBoxCover.getChildren().add(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);

        getChildren().add(vBoxCover);
        HBox.setHgrow(vBoxCover, Priority.ALWAYS);

        update();
        bindToController();
    }

    public void bindToSize(DoubleProperty width, DoubleProperty height) {
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
    }

    @Override
    public void update() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                /**
                 * Remove the previous chess.
                 * A little reminder: the first child of the gridBase should be the Chess itself.
                 */

                GridStatus positionPlayer = controller.getGridStatus(row, col);
                Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));

                //todo: change it to be modifiable
                Platform.runLater(() -> {
                    chess.setChessOwner(readBlockStatus(positionPlayer));
                });
            }

        }
        Log0j.writeLog("Board Updated.");
    }

    public void updateByGrid(int row, int col) {
        GridStatus positionPlayer = controller.getGridStatus(row, col);
        Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));
        Platform.runLater(() -> {
            chess.setChessOwner(readBlockStatus(positionPlayer));
        });
    }


    public void sourcedUpdate(int row, int col) {

        /**
         * Set the current chess
         */
        GridStatus positionPlayer = controller.getGridStatus(row, col);
        Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));
        chess.setChessOwner(readBlockStatus(positionPlayer));

        Task<Void> tasks[] = new Task[8];
        int cnt = 0;
        for (int stepRow = -1; stepRow <= 1; stepRow++) {
            for (int stepCol = -1; stepCol <= 1; stepCol++) {
                if (stepCol == 0 && stepRow == 0) {
                    continue;
                }
                final int stepR = stepRow, stepC = stepCol;
                tasks[cnt] = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        updateByDirection(row, col, stepR, stepC);
                        return null;
                    }
                };
                cnt++;
            }
        }
        for (int i = 0; i < 8; i++) {
            new Thread(tasks[i]).start();
        }
    }

    private void updateByDirection(int rowIndex, int colIndex, int stepRow, int stepCol) {
        int row = rowIndex, col = colIndex;
        while (row + stepRow >= 0 && row + stepRow < rowSize && col + stepCol >= 0 && col + stepCol < colSize) {
            row += stepRow;
            col += stepCol;

            GridStatus positionPlayer = controller.getGridStatus(row, col);
            Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));

            //todo: change it to be modifiable
            chess.setChessOwnerDirected(readBlockStatus(positionPlayer), stepCol, stepRow);

            try {
                Thread.sleep(CHESS_REVERSE_GAP_TIME);
            } catch (InterruptedException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * You should make sure that the board is valid.
     */
    public void judgeBoard() {
        int Player1Count = 0;
        int Player2Count = 0;
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                GridStatus positionPlayer = controller.getGridStatus(row, col);
                if (positionPlayer == GridStatus.PLAYER_1) {
                    Player1Count++;
                } else if (positionPlayer == GridStatus.PLAYER_2) {
                    Player2Count++;
                }
            }
        }
        Log0j.writeLog("Player 1 counted: " + Player1Count);
        Log0j.writeLog("Player 2 counted: " + Player2Count);

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));
                chess.setChessOwner(Chess.ChessOwner.PLACEHOLDER);
            }
        }

        /**
         * Listing Player 1 Chess
         */
        final int cnt1 = Player1Count;
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                placePlayerChess(cnt1, Chess.ChessOwner.PLAYER1, rowSize - 1, colSize - 1, -1, -1);
                return null;
            }
        }).start();

        /**
         * Listing Player 2 Chess
         */
        final int cnt2 = Player2Count;
        new Thread(new Task<Void>() {
            @Override
            protected Void call() {
                placePlayerChess(cnt2, Chess.ChessOwner.PLAYER2, 0, 0, 1, 1);
                return null;
            }
        }).start();
    }

    private void placePlayerChess(int cnt, Chess.ChessOwner player, int startRow, int startCol, int stepR, int stepC) {
        int cursorR = startRow, cursorC = startCol;
        while (cnt-- > 0) {
            Chess chess = ((Chess) gridBases[cursorR][cursorC].getChildren().get(0));
            Log0j.writeLog("Performing chess list: Coordinate: (" + cursorR + ", " + cursorC + ")");

            chess.setChessOwnerForceAnimated(player);

            try {
                Thread.sleep(CHESS_REVERSE_GAP_TIME);
            } catch (InterruptedException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
            }

            if (cursorC + stepC >= colSize || cursorC + stepC < 0) {
                if (cursorR + stepR >= rowSize || cursorR + stepR < 0) {
                    break;
                }
                cursorR += stepR;
                stepC = -stepC;
            } else {
                cursorC += stepC;
            }
        }
    }

    public Chess.ChessOwner readBlockStatus(GridStatus gridStatus) {
        if (gridStatus == GridStatus.PLAYER_1) {
            return Chess.ChessOwner.PLAYER1;
        } else if (gridStatus == GridStatus.PLAYER_2) {
            return Chess.ChessOwner.PLAYER2;
        } else {
            return Chess.ChessOwner.PLACEHOLDER;
        }
    }

    // Below are color updaters

    private void bindToController() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                final int tmpRow = row, tmpCol = col;
                gridBases[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent arg0) {
                        controller.onGridClick(tmpRow, tmpCol);

                    }
                });
            }
        }
        Log0j.writeLog("Bind to controller: " + controller);
    }

    public void performFinalJudge() {

    }
}