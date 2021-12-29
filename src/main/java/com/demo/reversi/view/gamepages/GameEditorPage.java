package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.gamemodel.ChessBoard;
import com.demo.reversi.controller.interfaces.GameEditor;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.UpdatableGame;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameEditorPage extends GridPane implements UpdatableGame {
    private final VBox container;
    private final ChessBoard chessBoard;

    private final MetroButton resizeButton;
    private final MetroButton resetButton;
    private final MetroButton saveButton;
    private final MetroButton brushNullButton;
    private final MetroButton brushBanningButton;
    private final MetroButton brushPlayer2Button;
    private final MetroButton brushPlayer1Button;
    private final MetroButton brushEmptyButton;

    private final GameSystemLayer gameSystem;
    private final GameEditor gameEditor;
    private final Theme theme;

    public GameEditorPage(GameSystemLayer gameSystem, Theme theme) {
        this.theme = theme;
        this.gameSystem = gameSystem;
        chessBoard = new ChessBoard(theme);
        gameEditor = gameSystem.getGameEditor();
        container = new VBox(5);

        chessBoard.initBoardPlayable(gameEditor);
        chessBoard.setShowAvailablePos(true);
        gameEditor.bindToGamePage(this);

        add(chessBoard, 0, 0);
        add(container, 1, 0);

        TextField rowText = new TextField("8");
        TextField colText = new TextField("8");
        resizeButton = new MetroButton("Resize", theme);
        brushEmptyButton = new MetroButton("Brush: Empty", theme);
        brushPlayer1Button = new MetroButton("Brush: Player1", theme);
        brushPlayer2Button = new MetroButton("Brush: Player2", theme);
        brushBanningButton = new MetroButton("Brush: Ban", theme);
        brushNullButton = new MetroButton("Brush: NULL", theme);
        saveButton = new MetroButton("Save Config", theme);
        resetButton = new MetroButton("Reset Board", theme);

        container.getChildren().addAll(
                new HBox(10, new TextLabel("RowSize", theme), rowText), new HBox(10, new TextLabel("ColSize", theme), colText),
                resizeButton, brushEmptyButton, brushPlayer1Button, brushPlayer2Button, brushBanningButton, brushNullButton,
                saveButton, resetButton);

        resizeButton.setOnAction(ActionEvent -> {
            gameEditor.resizeBoard(Integer.parseInt(rowText.getText()), Integer.parseInt(colText.getText()));
            Log0j.writeCaution("Reloading ChessBoard to size: " + Integer.parseInt(rowText.getText()) + ", " + Integer.parseInt(colText.getText()));
            chessBoard.initBoardPlayable(gameEditor);
        });
        brushEmptyButton.setOnAction(ActionEvent -> {
            gameEditor.setBrushAsEmptying();
        });
        brushPlayer1Button.setOnAction(ActionEvent -> {
            gameEditor.setBrushAsPlayer1();
        });
        brushPlayer2Button.setOnAction(ActionEvent -> {
            gameEditor.setBrushAsPlayer2();
        });
        brushBanningButton.setOnAction(ActionEvent -> {
            gameEditor.setBrushAsBanning();
        });
        brushNullButton.setOnAction(ActionEvent -> {
            gameEditor.setBrushAsNull();
        });
        saveButton.setOnAction(ActionEvent -> {
            gameSystem.saveConfig(gameEditor);
        });
        resetButton.setOnAction(ActionEvent -> {
            gameEditor.resetConfig();
            chessBoard.initBoardPlayable(gameEditor);
            update();
        });

        update();
    }

    @Override
    public void update() {
        chessBoard.update();
    }

    @Override
    public void performOnCloseAction() {
        gameEditor.saveConfig();
    }

    @Override
    public void sourcedUpdate(int row, int col) {
        chessBoard.sourcedUpdate(row, col);
    }

    @Override
    public void sourcedUpdate(int row, int col, Task<?> task) {
        chessBoard.sourcedUpdate(row, col, task);
    }

    @Override
    public void curtainCallUpdate() {
        chessBoard.curtainCall();
    }

    @Override
    public void callInterrupt(Interrupt interrupt, String reason) {
        PromptLoader.getGameInvalidInterruptAlert(reason, theme);
    }
}
