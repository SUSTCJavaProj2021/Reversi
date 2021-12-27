package com.demo.reversi.component.gamemodel;

import com.demo.reversi.controller.interfaces.GridStatus;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * You should not in any way directly manipulate the contents of the ChessBoard instance.
 */
public class ChessBoard extends HBox implements Updatable {
    public static final double DEFAULT_BOARD_MIN_SIZE = 400;
    public static final double CHESS_SIZE_RATIO = 0.800;
    public static final long CHESS_REVERSE_GAP_TIME = 70; //Unit: millis

    public final VBox vBoxCover;
    public final GridPane grid;
    public final double boardSize;

    public double cellMinSize;
    public int rowSize, colSize;
    public BoardGridComponent[][] gridBases;
    public GameControllerLayer controller;

    private final Theme theme;

    public boolean showAvailablePos = false;

    public ChessBoard(Theme theme) {
        this(theme, 0);
    }

    public ChessBoard(GameControllerLayer controller, Theme theme) {
        this(theme, 0);
        initBoardDemo(controller);
    }

    public ChessBoard(GameControllerLayer controller, Theme theme, double prefBoardSize) {
        this(theme, prefBoardSize);
        initBoardDemo(controller);
    }

    /**
     * New Game Initialization
     *
     * @param theme         the specific board theme
     * @param prefBoardSize the preferred board size
     */
    public ChessBoard(Theme theme, double prefBoardSize) {
        this.theme = theme;
        this.rowSize = 8;
        this.colSize = 8;
        grid = new GridPane();
        backgroundProperty().bind(theme.chessBoardBackgroundPR());

        if (prefBoardSize <= 0) {
            boardSize = DEFAULT_BOARD_MIN_SIZE;
        } else {
            boardSize = prefBoardSize;
        }

        reloadGrids();

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
    }

    public void reloadGrids() {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        cellMinSize = boardSize / this.rowSize;

        gridBases = new BoardGridComponent[rowSize][colSize];

        for (int row = 0; row < this.rowSize; row++) {
            gridBases[row] = new BoardGridComponent[this.colSize];
            for (int col = 0; col < this.colSize; col++) {

                //Initialize Grids
                gridBases[row][col] = new BoardGridComponent(theme);
                gridBases[row][col].setMinHeight(cellMinSize);
                gridBases[row][col].setMinWidth(cellMinSize);
                theme.bindToGridColor(gridBases[row][col].borderProperty());
                if ((row + col) % 2 == 0) {
                    theme.bindToChessBoardColor1(gridBases[row][col].backgroundProperty());
                } else {
                    theme.bindToChessBoardColor2(gridBases[row][col].backgroundProperty());
                }

                grid.add(gridBases[row][col], col, row);

                //Dynamic Chess Size
                Chess chess = new Chess(15, theme, Chess.ChessOwner.PLACEHOLDER);


                chess.playerPaint1PR().bind(theme.player1ChessColorPR());
                chess.playerPaint2PR().bind(theme.player2ChessColorPR());

                gridBases[row][col].getChildren().add(chess);
                StackPane.setAlignment(chess, Pos.CENTER);

                chess.chessSizePR.bind(Bindings.min(
                        gridBases[row][col].widthProperty().divide(2).multiply(CHESS_SIZE_RATIO),
                        gridBases[row][col].heightProperty().divide(2).multiply(CHESS_SIZE_RATIO)));
            }
        }
        Log0j.writeInfo("Grid background color all bound.");

        for (int row = 0; row < rowSize; row++) {
            grid.getRowConstraints().add(new RowConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                    Priority.SOMETIMES, VPos.CENTER, true));
        }
        for (int col = 0; col < colSize; col++) {
            grid.getColumnConstraints().add(new ColumnConstraints(cellMinSize, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
                    Priority.SOMETIMES, HPos.CENTER, true));
        }
        Log0j.writeInfo("Grid shape initialized.");
    }

    public void bindToSize(DoubleProperty width, DoubleProperty height) {
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
    }

    public void setShowIndicators(boolean value){
        showAvailablePos = value;
    }

    @Override
    public void update() {
        if (controller == null) {
            return;
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                updateByPosition(row, col);
            }
        }
        Log0j.writeInfo("Board Updated.");
    }

    public void updateByPosition(int row, int col) {
        if (controller == null) {
            return;
        }
        GridStatus gridStatus = controller.getGridStatus(row, col);
        Chess chess = ((Chess) gridBases[row][col].getChildren().get(1));
        chess.setChessOwner(readBlockStatus(gridStatus));
        final int r = row, c = col;
        updateGrid(gridStatus, r, c);
    }

    public void sourcedUpdate(int row, int col) {
        sourcedUpdate(row, col, null);
    }

    public void sourcedUpdate(int row, int col, Task<?> endingTask) {
        if (controller == null) {
            Log0j.writeError("GameController doesn't exist, and the board will not be updated.");
            return;
        }

        /*
         * Set the current chess
         */
        GridStatus gridStatus = controller.getGridStatus(row, col);
        Chess chess = ((Chess) gridBases[row][col].getChildren().get(1));
        chess.setChessOwner(readBlockStatus(gridStatus));

        List<Callable<Object>> tasks = new ArrayList<>();
        int cnt = 0;
        for (int stepRow = -1; stepRow <= 1; stepRow++) {
            for (int stepCol = -1; stepCol <= 1; stepCol++) {
                if (stepCol == 0 && stepRow == 0) {
                    continue;
                }
                final int stepR = stepRow, stepC = stepCol;
                tasks.add(Executors.callable(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        updateByDirection(row, col, stepR, stepC);
                        return null;
                    }
                }));
                cnt++;
            }
        }
        /*
         * Use the ExecutorService to run parallel animations.
         */
        ExecutorService es = Executors.newFixedThreadPool(8);
        new Thread(() -> {
            try {
                List<Future<Object>> answers = es.invokeAll(tasks);
                if (endingTask != null) {
                    new Thread(this::update).start();
                    new Thread(endingTask).start();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateByDirection(int rowIndex, int colIndex, int stepRow, int stepCol) {
        int row = rowIndex, col = colIndex;
        while (row + stepRow >= 0 && row + stepRow < rowSize && col + stepCol >= 0 && col + stepCol < colSize) {
            row += stepRow;
            col += stepCol;

            GridStatus gridStatus = controller.getGridStatus(row, col);
            Chess chess = ((Chess) gridBases[row][col].getChildren().get(1));


            chess.setChessOwnerDirected(readBlockStatus(gridStatus), stepCol, stepRow);

            try {
                Thread.sleep(CHESS_REVERSE_GAP_TIME);
            } catch (InterruptedException e) {
//                e.printStackTrace();
//                Thread.currentThread().interrupt();
            }
        }
    }

    public void curtainCall() {
        curtainCall(null);
    }


    /**
     * You should make sure that the board is valid.
     */
    public void curtainCall(Task<Void> endingTask) {
        if (controller == null) {
            return;
        }

        unloadController();
        int Player1Count = 0;
        int Player2Count = 0;
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                GridStatus gridStatus = controller.getGridStatus(row, col);
                if (gridStatus == GridStatus.PLAYER_1) {
                    Player1Count++;
                } else if (gridStatus == GridStatus.PLAYER_2) {
                    Player2Count++;
                }
                updateGrid(GridStatus.UNOCCUPIED, row, col);
            }
        }
        Log0j.writeInfo("Player 1 counted: " + Player1Count);
        Log0j.writeInfo("Player 2 counted: " + Player2Count);

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                Chess chess = ((Chess) gridBases[row][col].getChildren().get(1));
                chess.setChessOwner(Chess.ChessOwner.PLACEHOLDER);
            }
        }

        Log0j.writeInfo("Board rewriting completed");
        /**
         * Listing Player Chess
         */
        final int cnt1 = Player1Count;
        Task<Void> showPlayer1Chess = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log0j.writeInfo("Performing curtain call.");
                if (rowSize % 2 == 0) {
                    placePlayerChess(cnt1, Chess.ChessOwner.PLAYER1, rowSize - 1, 0, -1, 1);
                } else {
                    placePlayerChess(cnt1, Chess.ChessOwner.PLAYER1, rowSize - 1, colSize - 1, -1, -1);
                }
                return null;
            }
        };

        final int cnt2 = Player2Count;
        Task<Void> showPlayer2Chess = new Task<Void>() {
            @Override
            protected Void call() {
                placePlayerChess(cnt2, Chess.ChessOwner.PLAYER2, 0, 0, 1, 1);
                return null;
            }
        };

        showPlayer1Chess.setOnSucceeded(event -> new Thread(showPlayer2Chess).start());
        showPlayer2Chess.setOnSucceeded(event -> {
            Log0j.writeInfo("Curtain call completed.");
            if (endingTask != null) {
                Log0j.writeInfo("Ending task exists. Executing the task.");
                new Thread(endingTask).start();
            }
        });
        Log0j.writeInfo("Reached CurtainCall end. This info is for debug use.");
        new Thread(showPlayer1Chess).start();
    }

    private void placePlayerChess(int cnt, Chess.ChessOwner player, int startRow, int startCol, int stepR, int stepC) {
        int cursorR = startRow, cursorC = startCol;
        while (cnt-- > 0) {
            Chess chess = ((Chess) gridBases[cursorR][cursorC].getChildren().get(1));

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

    private void updateGrid(GridStatus gridStatus, int row, int col) {
        if(showAvailablePos){
            switch (gridStatus) {
                case AVAILABLE -> gridBases[row][col].setAvailable();
                case INVESTIGATING -> gridBases[row][col].setInvestigating();
                case BANNED -> gridBases[row][col].setBanned();
                default -> gridBases[row][col].setDefault();
            }
        }else{
            gridBases[row][col].setDefault();
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

    /**
     * Bind to its default controller
     */
    private void loadController() {
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
        Log0j.writeInfo("Loaded the controller: " + controller);
    }

    private void unloadController() {
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                final int tmpRow = row, tmpCol = col;
                gridBases[row][col].setOnMouseClicked(null);
            }
        }
        Log0j.writeInfo("Unloaded the controller.");
    }

    public void initBoardPlayable(GameControllerLayer controller) {
        unloadController();

        Log0j.writeInfo("Initializing the chessboard to be playable.");
        this.controller = controller;
        this.rowSize = controller.getRowSize();
        this.colSize = controller.getColSize();
        reloadGrids();
        loadController();
        update();
    }

    /**
     * Load the chessboard without the ability to perform onGridClick
     *
     * @param controller the target controller
     */
    public void initBoardDemo(GameControllerLayer controller) {
        Log0j.writeInfo("Initializing the chessboard to be in demo mode.");
        this.controller = controller;
        reloadGrids();
        update();
    }

}
