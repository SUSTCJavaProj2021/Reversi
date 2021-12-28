package com.demo.reversi.view.prompts;

import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.switches.IndicatedToggleSwitch;
import com.demo.reversi.controller.basic.player.Mode;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.gamepages.GameInfo;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PromptLoader {

    public static Alert getGameFinishAlert(GameControllerLayer controller, Theme theme) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Game Result");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getButtonTypes().add(ButtonType.OK);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);
        gridPane.add(new TitleLabel("Current Game has finished!", theme), 0, 0);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setOpacity(0);
        gridPane.add(separator1, 0, 1);

        String res = "Congratulations for player ";
        String resEnd = "for his extraordinary performance during the entire game.";
        String playerName = "";
        switch (controller.getGameStatus()) {
            case TIED -> {
                res = "Two players have tied. You are evenly powerful.";
                resEnd = "";
            }
            case WIN_PLAYER1 -> playerName = controller.getPlayer1().nameProperty().getValue();
            case WIN_PLAYER2 -> playerName = controller.getPlayer2().nameProperty().getValue();
            default -> {
                res = "This could be a result test, or debugging issue.";
                resEnd = "";
            }
        }
        TextLabel indicatorLabel = new TextLabel(res, theme);
        TitleLabel titleLabel = new TitleLabel(playerName, theme);
        TextLabel indicatorEndLabel = new TextLabel(resEnd, theme);
        gridPane.add(indicatorLabel, 0, 2);
        gridPane.add(titleLabel, 0, 3);
        gridPane.add(indicatorEndLabel, 0, 4);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setOpacity(0);
        gridPane.add(separator2, 0, 5);

        TextLabel label = new TextLabel("Go back to see more details.", theme);
        gridPane.add(label, 0, 6);


        //todo: optimize this interface
        return alert;
    }

    public static Alert getReplayGameAlert(Theme theme) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirmation");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);
        TitleLabel warningLabel = new TitleLabel("Do you want to replay the game from its beginning ?", theme);
        warningLabel.setWrapText(true);
        gridPane.add(warningLabel, 0, 0);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setOpacity(0);
        gridPane.add(separator1, 0, 1);

        //todo: optimize this interface
        return alert;
    }

    public static Alert getDeletePlayerAlert(PlayerLayer player, Theme theme) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Confirmation");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);
        TitleLabel warningLabel = new TitleLabel("Are you sure you want to delete player: " + player.nameProperty().getValue() + " ?", theme);
        warningLabel.setWrapText(true);
        gridPane.add(warningLabel, 0, 0);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setOpacity(0);
        gridPane.add(separator1, 0, 1);


        TextLabel label = new TextLabel("You understand that this procedure cannot be reversed.", theme);
        gridPane.add(label, 0, 2);

        //todo: optimize this interface
        return alert;
    }

    public static Dialog<String> getNewPlayerDialog(Theme theme) {
        Dialog<String> newPlayerDialog = new Dialog<>();
        newPlayerDialog.setTitle("Creating a new player");


        //Initialize layout
        DialogPane dialogPane = newPlayerDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);

        TitleLabel titleLabel = new TitleLabel("Provide the required info to create a new player.", theme);
        titleLabel.setWrapText(true);
        gridPane.add(titleLabel, 0, 0, 2, 1);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setOpacity(0);
        gridPane.add(separator, 0, 1, 2, 1);

        TextLabel label = new TextLabel("Player Name", theme);

        TextField textField = new TextField("New Player");

        gridPane.add(label, 0, 2);
        gridPane.add(textField, 1, 2);

        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(50 + (i * 2 - 1) * 15);
            colConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        //Set button content
        dialogPane.lookupButton(ButtonType.FINISH).disableProperty().bind(textField.textProperty().isEmpty());

        Platform.runLater(textField::requestFocus);

        //Set result format
        newPlayerDialog.setResultConverter((ButtonType buttonType) -> {
            if (buttonType == ButtonType.FINISH) {
                return textField.getText();
            }
            return null;
        });

        return newPlayerDialog;
    }

    public static Dialog<GameInfo> getGameInfoDialog(Theme theme) {
        Dialog<GameInfo> gameInfoDialog = new Dialog<>();
        gameInfoDialog.setTitle("Creating a new game");


        //Initialize layout
        DialogPane dialogPane = gameInfoDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);

        TitleLabel titleLabel = new TitleLabel("Provide the required info to complete the game setup.", theme);
        titleLabel.setWrapText(true);
        gridPane.add(titleLabel, 0, 0, GridPane.REMAINING, 1);

        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setOpacity(0);
        gridPane.add(separator, 0, 1, GridPane.REMAINING, 1);

        TextField[] textFields = new TextField[4];
        for (int i = 0; i < 4; i++) {
            textFields[i] = new TextField();
        }

        textFields[2].setText("8");
        textFields[3].setText("8");

        TextLabel[] labels = new TextLabel[4];
        for (int i = 0; i < 4; i++) {
            labels[i] = new TextLabel(theme);
        }


        labels[0].setText("Player 1 Name: ");
        labels[1].setText("Player 2 Name: ");
        labels[2].setText("Row size: (Integer)");
        labels[3].setText("Column Size: (Integer)");

        HBox[] container = new HBox[2];
        IndicatedToggleSwitch[] toggleSwitch = new IndicatedToggleSwitch[2];
        TextLabel[] indicatorLabels = new TextLabel[4];
        ComboBox<Mode>[] difficultyComboBox = new ComboBox[2];
        for (int i = 0; i < 2; i++) {
            indicatorLabels[i * 2] = new TextLabel("AI Toggle", theme);
            indicatorLabels[i * 2 + 1] = new TextLabel("Difficulty", theme);
            toggleSwitch[i] = new IndicatedToggleSwitch(theme);

            difficultyComboBox[i] = new ComboBox();
            for (Mode d : Mode.values()) {
                difficultyComboBox[i].getItems().add(d);
            }

            int finalI = i;

            toggleSwitch[i].switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue) {
                    difficultyComboBox[finalI].setDisable(false);
                    textFields[finalI].setText("");
                    textFields[finalI].setDisable(true);
                } else {
                    difficultyComboBox[finalI].getSelectionModel().select(0);
                    difficultyComboBox[finalI].setDisable(true);
                    textFields[finalI].setDisable(false);
                }
            }));

            difficultyComboBox[i].getSelectionModel().select(0);
            toggleSwitch[i].switchedOnProperty().setValue(false);

            container[i] = new HBox(10, indicatorLabels[i * 2], toggleSwitch[i], indicatorLabels[i * 2 + 1], difficultyComboBox[i]);
        }


        for (int i = 0; i < 2; i++) {
            gridPane.add(labels[i], 0, gridPane.getRowCount());
            gridPane.add(textFields[i], 1, gridPane.getRowCount() - 1);
            gridPane.add(container[i], 2, gridPane.getRowCount() - 1);
            GridPane.setMargin(container[i], new Insets(0, 0, 0, 10));
        }

        for (int i = 2; i < 4; i++) {
            gridPane.add(labels[i], 0, gridPane.getRowCount());
            gridPane.add(textFields[i], 1, gridPane.getRowCount() - 1);
        }

        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
//            colConstraints.setPercentWidth(50 + (i * 2 - 1) * 15);
            colConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConstraints);
        }


        //Set button content
        dialogPane.lookupButton(ButtonType.FINISH).disableProperty().bind(Bindings.createBooleanBinding(() ->
                textFields[0].getText().isEmpty() && !toggleSwitch[0].switchedOnProperty().getValue()
                        || textFields[1].getText().isEmpty() && !toggleSwitch[1].switchedOnProperty().getValue()
                        || textFields[2].getText().isEmpty()
                        || textFields[3].getText().isEmpty()
                        || !isInteger(textFields[2].getText())
                        || !isInteger(textFields[3].getText()),
                textFields[0].textProperty(), textFields[1].textProperty(), textFields[2].textProperty(), textFields[3].textProperty(),
                toggleSwitch[0].switchedOnProperty(), toggleSwitch[1].switchedOnProperty()));

        Platform.runLater(textFields[0]::requestFocus);



        //Set result format
        gameInfoDialog.setResultConverter((ButtonType buttonType) -> {
            if (buttonType == ButtonType.FINISH) {
                return new GameInfo(
                        textFields[0].getText(), toggleSwitch[0].switchedOnProperty().getValue(), difficultyComboBox[0].getValue(),
                        textFields[1].getText(), toggleSwitch[1].switchedOnProperty().getValue(), difficultyComboBox[1].getValue(),
                        Integer.parseInt(textFields[2].getText()), Integer.parseInt(textFields[3].getText()));
            }
            return null;
        });


        return gameInfoDialog;
    }

    public static Alert getGameInvalidInterruptAlert(Theme theme) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Game Interruption");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        dialogPane.setContent(gridPane);
        TitleLabel warningLabel = new TitleLabel("The game is invalid.", theme);
        warningLabel.setWrapText(true);
        gridPane.add(warningLabel, 0, 0);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setOpacity(0);
        gridPane.add(separator1, 0, 1);

        //todo: optimize this interface
        return alert;
    }

    private static boolean isInteger(String src) {
        try {
            Integer.parseInt(src);
            return true;
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return false;
        }
    }
}
