package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.statistics.PlayerItem;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import com.demo.reversi.view.prompts.PromptLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class GameSystemPage implements Updatable {
    public static final double TRANS_TIME_MILLIS = 70;
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final MetroButton createButton;
    public final MetroButton refreshButton;
    public final MetroButton loadButton;
    public final MetroButton saveButton;
    public final MetroButton saveToButton;
    public final MetroButton resetButton;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;

    public GameSystemPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        {
            {
                RowConstraints[] constraints = new RowConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new RowConstraints();
                    root.getRowConstraints().add(constraints[i]);
                }
                constraints[1].setVgrow(Priority.ALWAYS);
            }
            {
                ColumnConstraints[] constraints = new ColumnConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new ColumnConstraints();
                    constraints[i].setHgrow(Priority.ALWAYS);
                    root.getColumnConstraints().add(constraints[i]);
                }
            }
        }

        //finally
        root.add(new TitleLabel(LiteralConstants.StatisticsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);


        //todo: Change test
        TextLabel textLabel = new TextLabel("Click to delete player.", theme);
        addToConfig(textLabel);

        createButton = new MetroButton("New Player...", theme);
        addToConfig(createButton);

        refreshButton = new MetroButton("Refresh", theme);
        addToConfig(refreshButton);

        loadButton = new MetroButton("Load System", theme);
        addToConfig(loadButton);

        saveButton = new MetroButton("Save System", theme);
        addToConfig(saveButton);

        saveToButton = new MetroButton("Save System To..", theme);
        addToConfig(saveToButton);

        resetButton = new MetroButton("Reset System", theme);
        addToConfig(resetButton);

        initConfigs();
        refreshStats();
    }

    private void addToConfig(Node node) {
        configWrapper.add(node, configWrapper.getColumnCount(), 0);
    }


    private void initConfigs() {
        createButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Trying to create a new player.");
            Dialog<String> newPlayerDialog = PromptLoader.getNewPlayerDialog(theme);
            Optional<String> result = newPlayerDialog.showAndWait();
            result.ifPresent(gameSystem::createNewPlayer);
        });

        refreshButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Refreshing statistics.");
            refreshStats();
        });

        loadButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Refreshing statistics.");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load the GameSystem from");
            File file = fileChooser.showOpenDialog(root.getScene().getWindow());
            if (file != null) {
                gameSystem.load(file);
            } else {
                Log0j.writeError("File doesn't exist, and the attempt to load GameSystem failed.");
            }
        });
        saveButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Saving GameSystem.");
            gameSystem.save();
        });
        saveToButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Trying to save the GameSystem to...");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save the GameSystem to");
            File file = fileChooser.showSaveDialog(root.getScene().getWindow());
            if (file != null) {
                gameSystem.saveTo(file);
            } else {
                Log0j.writeError("Location doesn't exist or an unknown error happened. The attempt to save GameSystem failed.");
            }
        });
        resetButton.setOnAction(ActionEvent -> {
            Log0j.writeInfo("Resetting GameSystem.");
            gameSystem.reset();
        });
    }

    private void refreshStats() {
        Log0j.writeInfo("Stats refreshing triggered.");
        contentWrapper.getChildren().clear();
        List<PlayerLayer> playerLayerList = gameSystem.queryPlayerInfoAllSorted();
        Log0j.writeInfo("Player List count: " + playerLayerList.size());
        //Initialize header
        contentWrapper.add(new PlayerItem(theme), 0, 0);

        //Add player items
        int cnt = 1;
        for (PlayerLayer player : playerLayerList) {
            Log0j.writeInfo("Player Item added: " + player.nameProperty() + ", given index: " + contentWrapper.getRowCount());

            PlayerItem playerItem = new PlayerItem(cnt++, player, theme);
            playerItem.setOnMouseClicked(ActionEvent -> {
                Alert alert = PromptLoader.getDeletePlayerAlert(player, theme);
                alert.showAndWait()
                        .filter(response -> response == ButtonType.YES)
                        .ifPresent(response -> gameSystem.delPlayer(player.nameProperty().getValue()));
            });

            contentWrapper.add(playerItem, 0, contentWrapper.getRowCount());
        }
    }


    @Override
    public void update() {
        refreshStats();
    }
}
