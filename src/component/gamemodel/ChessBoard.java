package component.gamemodel;

import controller.BlockStatus;
import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import controller.logger.Log0j;
import model.Chess;
import view.Theme;
import view.Updatable;

public class ChessBoard extends GridPane implements Updatable {
    public static final double DEFAULT_BOARD_MIN_SIZE = 400;
    public static final double CHESS_SIZE_RATIO = 0.618;

    public StackPane grid[][];
    public int rowSize, colSize;
    public GameController controller;
    public final double cellMinSize;
    public final double boardSize;

    public Theme theme;

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

        if (prefBoardSize <= 0) {
            boardSize = DEFAULT_BOARD_MIN_SIZE;
        } else {
            boardSize = prefBoardSize;
        }

        cellMinSize = boardSize / this.rowSize;

        grid = new StackPane[rowSize][colSize];

        for (int row = 0; row < this.rowSize; row++) {
            grid[row] = new StackPane[this.colSize];
            for (int col = 0; col < this.colSize; col++) {

                final int tmpRow = row, tmpCol = col;

                //Initialize Grids
                grid[row][col] = new StackPane();
                grid[row][col].setMinHeight(cellMinSize);
                grid[row][col].setMinWidth(cellMinSize);
                theme.bindToBorderPaint(grid[row][col].borderProperty());
                if ((row + col) % 2 == 0) {
                    theme.bindToChessBoardPaint1(grid[row][col].backgroundProperty());
                } else {
                    theme.bindToChessBoardPaint2(grid[row][col].backgroundProperty());
                }

                this.add(grid[row][col], col, row);

                //Dynamic Chess Size
                Chess chess = new Chess(15, Color.TRANSPARENT);
                grid[row][col].getChildren().add(chess);
                chess.radiusProperty().bind(Bindings.min(
                        grid[row][col].widthProperty().divide(2).multiply(CHESS_SIZE_RATIO),
                        grid[row][col].heightProperty().divide(2).multiply(CHESS_SIZE_RATIO)));
            }
        }
        Log0j.writeLog("Grid background color all bound.");

        for (int row = 0; row < rowSize; row++) {
            getRowConstraints().add(new RowConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                    Priority.SOMETIMES, VPos.CENTER, true));
        }
        for (int col = 0; col < colSize; col++) {
            getColumnConstraints().add(new ColumnConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                            Priority.SOMETIMES, HPos.CENTER, true));
        }
        Log0j.writeLog("Grid shape initialized.");

        this.setMinWidth(boardSize);
        this.setMinHeight(boardSize);
        this.setPrefWidth(GridPane.USE_COMPUTED_SIZE);
        this.setPrefHeight(GridPane.USE_COMPUTED_SIZE);

        update();
        bindToController();

    }

    public void bindToSize(DoubleProperty width ,DoubleProperty height){
        prefWidthProperty().bind(width);
        prefHeightProperty().bind(height);
    }

    @Override
    public void update() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                //Remove the previous chess.
                if (grid[row][col].getChildren().size() > 1) {
                    grid[row][col].getChildren().remove(1);
                }

                BlockStatus currentPlayer = controller.getBlockStatus(row, col);
                Chess chess = ((Chess) grid[row][col].getChildren().get(0));

                if (currentPlayer == BlockStatus.WHITE_PLAYER.WHITE_PLAYER) {
                    chess.setColor(Color.WHITE);
                } else if (currentPlayer == BlockStatus.BLACK_PLAYER.BLACK_PLAYER) {
                    chess.setColor(Color.BLACK);
                } else {
                    chess.setColor(Color.TRANSPARENT);
                }
            }

        }
        Log0j.writeLog("Board Updated.");
    }

    // Below are color updaters

    private void bindToController() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                final int tmpRow = row, tmpCol = col;
                grid[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
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
