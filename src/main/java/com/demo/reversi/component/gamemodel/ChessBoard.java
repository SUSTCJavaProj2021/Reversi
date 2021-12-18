package com.demo.reversi.component.gamemodel;

import com.demo.reversi.controller.BlockStatus;
import com.demo.reversi.controller.GameController;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
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

/**
 * You should not in any way directly manipulate the contents of the ChessBoard instance.
 */
public class ChessBoard extends HBox implements Updatable {
    public static final double DEFAULT_BOARD_MIN_SIZE = 400;
    public static final double CHESS_SIZE_RATIO = 0.618;

    public final VBox vBoxCover;
    public final GridPane grid;
    public final BoardGridComponent gridBases[][];
    public final int rowSize, colSize;
    public final GameController controller;
    public final double cellMinSize;
    public final double boardSize;

    private final Theme theme;

    public ChessBoard(GameController controller, Theme theme) {
        this(controller, theme, 0);
    }

    /**
     * New Game Initialization
     *
     * @param controller    the specific GameController
     * @param theme         the specific board theme
     * @param prefBoardSize the preferred board size
     */
    public ChessBoard(GameController controller, Theme theme, double prefBoardSize) {
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
                 */

                BlockStatus positionPlayer = controller.getBlockStatus(row, col);
                Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));

                //todo: change it to be modifiable
                Platform.runLater(()->{
                    chess.setChessOwner(readBlockStatus(positionPlayer));
                });
            }

        }
        Log0j.writeLog("Board Updated.");
    }

    public void sourcedUpdate(int row, int col) {

        /**
         * Set the current chess
         */
        BlockStatus positionPlayer = controller.getBlockStatus(row, col);
        Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));
        Platform.runLater(()->{
            chess.setChessOwner(readBlockStatus(positionPlayer));
        });

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

            BlockStatus positionPlayer = controller.getBlockStatus(row, col);
            Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));
            //todo: change it to be modifiable
            Platform.runLater(() -> {
                chess.setChessOwnerDirected(readBlockStatus(positionPlayer), stepCol, stepRow);
            });
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
            }
        }
    }

    public Chess.ChessOwner readBlockStatus(BlockStatus blockStatus) {
        if (blockStatus == BlockStatus.BLACK_PLAYER) {
            return Chess.ChessOwner.PLAYER1;
        } else if (blockStatus == BlockStatus.WHITE_PLAYER) {
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
}
