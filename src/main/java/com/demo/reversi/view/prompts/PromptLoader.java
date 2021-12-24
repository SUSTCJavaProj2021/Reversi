package com.demo.reversi.view.prompts;

import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.gamepages.GameInfo;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class PromptLoader {

    public static Alert getGameFinishAlert(Theme theme) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        DialogPane dialogPane = alert.getDialogPane();


        //todo: optimize this interface
        alert.setContentText("WDNMD!");
        return alert;
    }

    public static Dialog<GameInfo> getGameInfoDialog(Theme theme) {
        Dialog<GameInfo> gameInfoDialog = new Dialog<>();
        gameInfoDialog.setTitle("Creating a new game");


        //Initialize layout
        DialogPane dialogPane = gameInfoDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        TextField[] textFields = new TextField[4];
        for (int i = 0; i < 4; i++) {
            textFields[i] = new TextField();
        }

        textFields[2].setText("8");
        textFields[3].setText("8");

        Label[] labels = new Label[4];
        for (int i = 0; i < 4; i++) {
            labels[i] = new Label();
            labels[i].fontProperty().bind(theme.textFontFamilyPR());
            labels[i].textFillProperty().bind(theme.textFontPaintPR());
        }


        labels[0].setText("Player 1 Name: ");
        labels[1].setText("Player 2 Name: ");
        labels[2].setText("Row size: (Integer)");
        labels[3].setText("Column Size: (Integer)");
        dialogPane.backgroundProperty().bind(theme.backPanePR());

        GridPane gridPane = new GridPane();
        TitleLabel titleLabel = new TitleLabel("Provide the required info to complete the game setup.", theme);
        titleLabel.setWrapText(true);
        gridPane.add(titleLabel, 0, 0, 2, 1);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setOpacity(0);
        gridPane.add(separator, 0, 1, 2, 1);

        for (int i = 0; i < 4; i++) {
            gridPane.add(labels[i], 0, gridPane.getRowCount());
            gridPane.add(textFields[i], 1, gridPane.getRowCount() - 1);
        }

        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(50 + (i * 2 - 1) * 15);
            colConstraints.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConstraints);
        }


        //Set button content
        dialogPane.lookupButton(ButtonType.FINISH).disableProperty().bind(Bindings.createBooleanBinding(() -> {
            return textFields[0].getText().isEmpty()
                    && textFields[1].getText().isEmpty()
                    && textFields[2].getText().isEmpty()
                    && textFields[3].getText().isEmpty()
                    && isInteger(textFields[2].getText())
                    && isInteger(textFields[3].getText());
        }, textFields[0].textProperty(), textFields[1].textProperty(), textFields[2].textProperty(), textFields[3].textProperty()));

        Platform.runLater(textFields[0]::requestFocus);


        //Set result format
        dialogPane.setContent(gridPane);
        gameInfoDialog.setResultConverter((ButtonType buttonType) -> {
            if (buttonType == ButtonType.FINISH) {
                return new GameInfo(textFields[0].getText(), textFields[1].getText(), Integer.parseInt(textFields[2].getText()), Integer.parseInt(textFields[3].getText()));
            }
            return null;
        });


        return gameInfoDialog;
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
