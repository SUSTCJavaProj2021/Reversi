package com.demo.reversi.view.gamepages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.controller.interfaces.GameControllerLayer;
import com.demo.reversi.controller.interfaces.GameSystemLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class TutorialPage extends GamePageLocal {

    public static final Background coverBackground = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.50), null, null));

    public final BorderPane root;
    public final StackPane superContainer;
    public final StackPane viewCover;
    public final HBox buttonContainer;
    public final MetroButton nextButton;
    public final MetroButton previousButton;
    public final MetroButton cancelButton;

    public final Robot robot;

    private final IntegerProperty step;

    public TutorialPage(GameSystemLayer gameSystem, GameControllerLayer controller, Theme theme) {
        super(gameSystem, controller, theme);
        root = new BorderPane();
        superContainer = new StackPane();
        viewCover = new StackPane();
        nextButton = new MetroButton("Next", theme);
        previousButton = new MetroButton("Previous", theme);
        cancelButton = new MetroButton("Cancel", theme);
        robot = new Robot();
        step = new SimpleIntegerProperty(0);

        root.backgroundProperty().bind(theme.backPanePR());

        superContainer.getChildren().addAll(super.root, viewCover);
        root.setCenter(superContainer);

        buttonContainer = new HBox(20);
        buttonContainer.getChildren().addAll(new Region(), previousButton, nextButton, cancelButton);
        root.setBottom(buttonContainer);
        BorderPane.setAlignment(buttonContainer, Pos.CENTER_RIGHT);

        viewCover.setBackground(coverBackground);

        initRelations();
        initTutorial();
    }

    private void initRelations() {
        step.addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() <= 1) {
                previousButton.setDisable(true);
                previousButton.setOpacity(0.50);
            } else if (newValue.intValue() == 7) {
                nextButton.setText("Finish");
                nextButton.setOnAction(ActionEvent -> {
                    ((Stage) root.getScene().getWindow()).close();
                });
            } else {
                previousButton.setOpacity(1.00);
                previousButton.setDisable(false);
                nextButton.setText("Next");
                nextButton.setOnAction(ActionEvent -> {
                    Platform.runLater(this::callTutorialStepIn);
                });
            }
        });
        nextButton.setOnAction(ActionEvent -> {
            Platform.runLater(this::callTutorialStepIn);
        });
        previousButton.setOnAction(ActionEvent -> {
            step.setValue(step.getValue() - 2);
            Platform.runLater(this::callTutorialStepIn);
        });
        cancelButton.setOnAction(ActionEvent -> {
            ((Stage) root.getScene().getWindow()).close();
        });
    }

    /**
     * I know the implementation may seem stupid, but this is by far the quickest solution.
     */

    private void callTutorialStepIn() {
        switch (step.getValue()) {
            case 0 -> {
                viewCover.setVisible(true);
                viewCover.getChildren().clear();
                TitleLabel introText = new TitleLabel("Welcome to Reversi!\nThis tutorial will guide you through the basic gameplay.", theme);
                introText.setPrefHeight(Control.USE_COMPUTED_SIZE);
                viewCover.getChildren().add(introText);
                StackPane.setAlignment(introText, Pos.CENTER);
            }
            case 1 -> {
                viewCover.setVisible(true);
                viewCover.getChildren().clear();
                TitleLabel introText = new TitleLabel("In this game, " +
                        "your goal is to keep the number of your chess on the board as much as possible.", theme);
                introText.setPrefHeight(Control.USE_COMPUTED_SIZE);
                viewCover.getChildren().add(introText);
                StackPane.setAlignment(introText, Pos.CENTER);
            }
            case 2 -> {
                viewCover.setVisible(true);
                viewCover.getChildren().clear();
                TitleLabel introText = new TitleLabel("As the name \"Reversi!\" suggests,\n" +
                        "you can \"flip\" your opponent's chess, " +
                        "by placing a new chess such that\n" +
                        "you can cap his chess exactly between your chess.", theme);
                introText.setPrefHeight(Control.USE_COMPUTED_SIZE);
                viewCover.getChildren().add(introText);
                StackPane.setAlignment(introText, Pos.CENTER);
            }
            case 3 -> {
                viewCover.setVisible(true);
                viewCover.getChildren().clear();
                TitleLabel introText = new TitleLabel("Place your chess on the highlighted position, and see what will happen.\n" +
                        "Click next to place it on the ChessBoard.", theme);
                highlightGrid(3, 2);
                introText.setPrefHeight(Control.USE_COMPUTED_SIZE);
                viewCover.getChildren().add(introText);
                StackPane.setAlignment(introText, Pos.TOP_CENTER);
            }
            case 4, 6 -> {
                viewCover.setVisible(false);
            }
            case 5 -> {
                viewCover.setVisible(true);
                viewCover.getChildren().clear();
                TitleLabel introText = new TitleLabel("You have got familiar with its only rule! Now it's time to play some new game.\n" +
                        "If you want to finish this game, just click \"Next\" and, stay and play.\n" +
                        "Otherwise, click first \"Next\" then \"Finish\" to exit the tutorial.", theme);
                introText.setPrefHeight(Control.USE_COMPUTED_SIZE);
                viewCover.getChildren().add(introText);
                StackPane.setAlignment(introText, Pos.TOP_CENTER);
            }
            default -> {
                Log0j.writeInfo("Stepping into a impossible scenario on the TutorialPage. This shouldn't have happened.");
            }

        }
        step.setValue(step.getValue() + 1);
    }

    private void highlightGrid(int row, int col) {
        Bounds bnds = chessBoard.gridBases[row][col].localToScene(chessBoard.gridBases[row][col].getBoundsInParent());
        double x = bnds.getMinX();
        double y = bnds.getMinX();
        Circle circle = new Circle(Math.min(superContainer.getWidth() * 0.5, superContainer.getHeight() * 0.5), Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(10);
        viewCover.getChildren().add(circle);
        StackPane.setAlignment(circle, Pos.CENTER);

        TranslateTransition trans = new TranslateTransition(Duration.seconds(1), circle);
//        trans.setFromX(root.getWidth() * 0.5);
//        trans.setFromY(root.getHeight() * 0.5);
        trans.setToX(0);
        trans.setToY(0);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(1), circle);
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(0);
        scale.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition(trans, scale);
        parallelTransition.setCycleCount(1);
        Platform.runLater(parallelTransition::play);
        parallelTransition.setOnFinished(ActionEvent -> viewCover.getChildren().remove(circle));
    }


    private void initTutorial() {
        callTutorialStepIn();
    }
}
