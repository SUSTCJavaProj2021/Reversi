package com.demo.reversi.component.panes;

import com.demo.reversi.controller.interfaces.PlayerLayer;
import com.demo.reversi.controller.local.SimplePlayer;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;

/**
 * InfoPane defines a layout that shows player's info and is wrapped in a single node.
 */
public class InfoPane extends StackPane implements Updatable {
    private static final double TRANS_TIME_MILLIS = 150;
    public static final double CORNER_RADII = 15;
    public static final double PREF_HEIGHT = 80;
    public static final double PREF_WIDTH = 300;
    public static final double VIEW_COVER_OPACITY = 0.6;
    public static final double INDICATOR_RATIO = 0.6;

    /**
     * The background which displays player color
     */
    public final StackPane viewCover;

    /**
     *
     */
    public final StackPane statusCover;

    /**
     * The background on which the status of the player is indicated
     */
    public final StackPane indicator;

    /**
     * Used to format the labels
     */
    public final GridPane rootView;
    public final Label playerNameLabel;
    public final Label playerInfoLabel;
    public final ObjectProperty<Paint> playerColorPR;
    public final ImageView playerImage;
    public final ProgressIndicator progressIndicator;
    public final BooleanProperty isActivated;

    public PlayerLayer player;

    public BooleanProperty isActivatedProperty() {
        return isActivated;
    }

    private final Theme theme;

    public InfoPane(Theme theme, ObjectProperty<Color> playerColor) {
        this(new SimplePlayer("Unknown"), theme, playerColor);
    }

    public InfoPane(PlayerLayer player, Theme theme, ObjectProperty<Color> playerColor) {
        this.theme = theme;
        setMinHeight(Control.USE_PREF_SIZE);
        setPrefHeight(PREF_HEIGHT);
        setPrefWidth(PREF_WIDTH);

        viewCover = new StackPane();
        statusCover = new StackPane();
        indicator = new StackPane();
        rootView = new GridPane();
        /**
         * Initialize background
         */
        viewCover.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            //
            Stop stops[] = new Stop[]{new Stop(0, playerColor.getValue()), new Stop(1, Color.TRANSPARENT)};
            LinearGradient fill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            return new Background(new BackgroundFill(fill, new CornerRadii(CORNER_RADII), null));
        }, playerColor));

        viewCover.setOpacity(VIEW_COVER_OPACITY);

        playerColorPR = new SimpleObjectProperty<>();
        playerColorPR.bind(playerColor);

        playerNameLabel = new Label();
        playerNameLabel.fontProperty().bind(theme.infoTitleFontFamilyPR());
        playerNameLabel.textFillProperty().bind(theme.titleFontPaintPR());

        playerInfoLabel = new Label();
        playerInfoLabel.fontProperty().bind(Bindings.createObjectBinding(() -> {
            return new Font(theme.titleFontFamilyPR().getValue().getFamily(), 16);
        }, theme.titleFontFamilyPR()));
        playerInfoLabel.textFillProperty().bind(theme.titleFontPaintPR());

        isActivated = new SimpleBooleanProperty(false);
        playerImage = new ImageView();
        playerImage.imageProperty().bind(theme.playerIconPR());
        playerImage.setPreserveRatio(true);

        //Design leads to it
        playerImage.fitWidthProperty().bind(Bindings.min(widthProperty().multiply(0.4), heightProperty().multiply(0.85)));


        progressIndicator = new ProgressIndicator();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-1); //Makes all non-white colors black
        indicator.setEffect(colorAdjust);
        indicator.maxHeightProperty().bind(heightProperty().multiply(INDICATOR_RATIO));
        indicator.maxWidthProperty().bind(indicator.maxHeightProperty());

        //Set the player
        setPlayer(player);
        init();
    }

    /**
     * Init relations so that GUI doesn't need to frequently call its <code>update()</code> method.
     */
    public void init() {
        initLayout();
        isActivated.addListener((observable, oldValue, newValue) -> {
            //todo: Add animation
            if (isActivated.getValue()) {
                Platform.runLater(() -> indicator.getChildren().add(progressIndicator));
            } else {
                Platform.runLater(() -> indicator.getChildren().clear());
            }
        });

    }

    public void initLayout() {
        getChildren().addAll(viewCover, statusCover, indicator, rootView);
        {
            ColumnConstraints constraints[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                constraints[i] = new ColumnConstraints();
                rootView.getColumnConstraints().add(constraints[i]);
            }
            constraints[0].setPercentWidth(35);
        }


        {
            RowConstraints constraints[] = new RowConstraints[3];
            for (int i = 0; i < 3; i++) {
                constraints[i] = new RowConstraints();
                rootView.getRowConstraints().add(constraints[i]);
            }
            constraints[0].setPercentHeight(67);
            constraints[1].setPercentHeight(33);
        }


        rootView.add(playerImage, 0, 0);
        GridPane.setConstraints(playerImage, 0, 0, 1, GridPane.REMAINING,
                HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);


        rootView.add(playerNameLabel, 1, 0);
        GridPane.setConstraints(playerNameLabel, 1, 0, 1, 1,
                HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);
        playerNameLabel.scaleYProperty().bind(playerNameLabel.scaleXProperty());
        playerNameLabel.scaleXProperty().bind(widthProperty().divide(PREF_WIDTH));


        rootView.add(playerInfoLabel, 1, 1);
        GridPane.setConstraints(playerInfoLabel, 1, 1, 1, 1,
                HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);
        playerInfoLabel.scaleYProperty().bind(playerNameLabel.scaleXProperty());
        playerInfoLabel.scaleXProperty().bind(widthProperty().divide(PREF_WIDTH));
    }

    public void setPlayer(PlayerLayer player) {
        this.player = player;
        playerNameLabel.textProperty().bind(player.nameProperty());
        playerInfoLabel.textProperty().bind(Bindings.createObjectBinding(() -> {
            return String.format("%3dW / %3dL  %3.2f", player.totalMatchCountProperty().getValue(), player.totalWinCountProperty().getValue(),
                    player.overallWinRateProperty().getValue());
        }, player.totalMatchCountProperty(), player.totalWinCountProperty(), player.overallWinRateProperty()));
    }

    public InfoPane outer() {
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void performOnCloseAction() {

    }

    public enum Status {
        WINNER, TIED, LOSER;
    }

    public void setFinished(Status status) {
        Platform.runLater(() -> indicator.getChildren().clear());
        if (status == Status.WINNER) {
            Platform.runLater(() ->
                    statusCover.setBackground(new Background(
                            new BackgroundFill(Color.rgb(255, 235, 37, 0.39), new CornerRadii(CORNER_RADII), null))));
        } else if (status == Status.TIED) {
            Platform.runLater(() ->
                    statusCover.setBackground(new Background(
                            new BackgroundFill(Color.rgb(233, 233, 233, 0.39), new CornerRadii(CORNER_RADII), null))));
        }
    }

    public void reInit() {
        statusCover.setBackground(null);
    }

}
