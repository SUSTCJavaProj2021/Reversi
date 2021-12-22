package com.demo.reversi.component.panes;

import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.controller.local.SimplePlayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.skin.ProgressIndicatorSkin;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class InfoPane extends StackPane implements Updatable {
    private static final double TRANS_TIME_MILLIS = 150;
    public static final double CORNER_RADII = 15;
    public static final double PREF_HEIGHT = 80;
    public static final double PREF_WIDTH = 130;
    public static final double INDICATOR_RATIO= 0.6;

    public final StackPane viewCover;
    public final StackPane indicator;
    public final GridPane rootView;
    public final Label playerNameLabel;
    public final Label playerWinRateLabel;
    public final ObjectProperty<Paint> playerColorPR;
    public final ImageView playerImage;
    public final BooleanProperty isActivated;

    public BooleanProperty isActivatedProperty() {
        return isActivated;
    }

    private final Theme theme;

    public InfoPane(Theme theme, ObjectProperty<Color> playerColor){
        this(new SimplePlayer("Unknown"), theme, playerColor);
    }

    public InfoPane(PlayerLayer player, Theme theme, ObjectProperty<Color> playerColor) {
        this.theme = theme;
        setMinHeight(Control.USE_PREF_SIZE);
        setPrefHeight(PREF_HEIGHT);
        setPrefWidth(PREF_WIDTH);

        viewCover = new StackPane();
        indicator = new StackPane();
        rootView = new GridPane();
        getChildren().addAll(viewCover, indicator, rootView);
        /**
         * Initialize background
         */
        viewCover.backgroundProperty().bind(Bindings.createObjectBinding(() -> {
            //
            Stop stops[] = new Stop[]{new Stop(0, playerColor.getValue()), new Stop(1, Color.TRANSPARENT)};
            LinearGradient fill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            return new Background(new BackgroundFill(fill, new CornerRadii(CORNER_RADII), null));
        }, playerColor));

        viewCover.setOpacity(0.6);

        playerColorPR = new SimpleObjectProperty<>();
        playerColorPR.bind(playerColor);

        playerNameLabel = new Label();
        playerNameLabel.textProperty().bind(player.nameProperty());
        playerNameLabel.fontProperty().bind(theme.infoTitleFontFamilyPR());
        playerNameLabel.textFillProperty().bind(theme.titleFontPaintPR());

        playerWinRateLabel = new Label();
        playerWinRateLabel.fontProperty().bind(Bindings.createObjectBinding(() -> {
            return new Font(theme.titleFontFamilyPR().getValue().getFamily(), 16);
        }, theme.titleFontFamilyPR()));
        playerWinRateLabel.textFillProperty().bind(theme.titleFontPaintPR());
        //todo: Change this test
        playerWinRateLabel.setText("5W / 4L, 55.6%");


        isActivated = new SimpleBooleanProperty(false);
        playerImage = new ImageView();
        playerImage.imageProperty().bind(theme.playerIconPR());
        playerImage.setPreserveRatio(true);

        //Design leads to it
        playerImage.fitWidthProperty().bind(Bindings.min(widthProperty().multiply(0.4), heightProperty().multiply(0.85)));


        init();
    }

    public void init() {
        initLayout();
        isActivated.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //todo: Add animation
                if (isActivated.getValue()) {
                    ProgressIndicator progressIndicator = new ProgressIndicator();
                    indicator.getChildren().add(progressIndicator);
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-1); //Makes all non-white colors black
                    indicator.setEffect(colorAdjust);
                    indicator.prefHeightProperty().bind(heightProperty().multiply(INDICATOR_RATIO));
                    indicator.prefWidthProperty().bind(indicator.prefHeightProperty());
                } else {
                    indicator.getChildren().clear();
                }
            }
        });
    }

    public void initLayout() {
        {
            ColumnConstraints constraints[] = new ColumnConstraints[2];
            for (int i = 0; i < 2; i++) {
                constraints[i] = new ColumnConstraints();
                rootView.getColumnConstraints().add(constraints[i]);
            }
            constraints[1].setHgrow(Priority.ALWAYS);
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
        GridPane.setConstraints(playerImage, 0, 0, 1,
                GridPane.REMAINING, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);


        rootView.add(playerNameLabel, 1, 0);
        GridPane.setConstraints(playerNameLabel, 1, 0, 1,
                1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);
        rootView.add(playerWinRateLabel, 1, 1);


        GridPane.setConstraints(playerWinRateLabel, 1, 1, 1,
                1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS, null);
    }

    public void setPlayer(PlayerLayer player){
        playerNameLabel.textProperty().bind(player.nameProperty());
        playerWinRateLabel.textProperty().setValue("QX3 LOAD TEST");
    }

    public InfoPane outer() {
        return this;
    }

    @Override
    public void update() {

    }

}
