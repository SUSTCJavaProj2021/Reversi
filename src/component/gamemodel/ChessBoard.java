package component.gamemodel;

import controller.BlockStatus;
import controller.GameController;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import controller.logger.Log0j;
import view.Theme;
import view.Updatable;

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
                Chess chess = new Chess(15, Color.TRANSPARENT);
                gridBases[row][col].getChildren().add(chess);
                chess.radiusProperty().bind(Bindings.min(
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
        setAlignment(Pos.CENTER);
        vBoxCover.setAlignment(Pos.CENTER);

        final NumberBinding binding = Bindings.min(widthProperty(), heightProperty());
        vBoxCover.prefWidthProperty().bind(binding);
        vBoxCover.prefHeightProperty().bind(binding);
        vBoxCover.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        vBoxCover.setFillWidth(true);
        VBox.setVgrow(grid, Priority.ALWAYS);
        vBoxCover.getChildren().add(grid);
        getChildren().add(vBoxCover);
        HBox.setHgrow(this, Priority.ALWAYS);

        update();
        bindToController();
    }

    public void bindToSize(DoubleProperty width ,DoubleProperty height){
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
    }

    @Override
    public void update() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                //Remove the previous chess.
                if (gridBases[row][col].getChildren().size() > 1) {
                    gridBases[row][col].getChildren().remove(1);
                }

                BlockStatus currentPlayer = controller.getBlockStatus(row, col);
                Chess chess = ((Chess) gridBases[row][col].getChildren().get(0));

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
