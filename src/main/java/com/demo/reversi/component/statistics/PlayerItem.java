package com.demo.reversi.component.statistics;

import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.themes.Theme;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class PlayerItem extends GridPane {
    public static final double DEFAULT_HEIGHT = 45;
    public final static double OPACITY_DEFAULT = 0.90;
    public final static double OPACITY_SELECTED = 1.0;

    public static final double TRANS_TIME_MILLIS = 100;

    public final Label playerRanking;
    public final Label playerName;
    public final Label playerTotalMatchCnt;
    public final Label playerTotalWinCnt;
    public final Label playerOverallWinRate;
    public final Label playerPVPWinCnt;
    public final Label playerPVPWinRate;
    public final Label playerPVEWinCnt;
    public final Label playerPVEWinRate;

    private final Theme theme;

    public PlayerItem(int index, PlayerLayer player, Theme theme) {
        this.theme = theme;
        setMaxHeight(DEFAULT_HEIGHT);

        /**
         * To make the items have a visual separator
         */
        if (index <= 3) {
            Stop[] stops;
            LinearGradient coverFill;
            stops = switch (index) {
                case 1 -> new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(255, 238, 0, 0.30)), new Stop(1, Color.TRANSPARENT)};
                case 2 -> new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(255, 255, 255, 0.30)), new Stop(1, Color.TRANSPARENT)};
                case 3 -> new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(143, 88, 4, 0.30)), new Stop(1, Color.TRANSPARENT)};
                default -> new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(255, 255, 255, 0.08)), new Stop(1, Color.TRANSPARENT)};
            };
            coverFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            setBackground(new Background(new BackgroundFill(coverFill, null, null)));
        } else if (index % 2 == 0) {
            Stop[] stops = new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(255, 255, 255, 0.08)), new Stop(1, Color.TRANSPARENT)};
            LinearGradient coverFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
            setBackground(new Background(new BackgroundFill(coverFill, null, null)));
        }

        /**
         * Initialize labels
         */
        playerRanking = new Label();
        playerName = new Label();
        playerTotalMatchCnt = new Label();
        playerTotalWinCnt = new Label();
        playerOverallWinRate = new Label();
        playerPVPWinCnt = new Label();
        playerPVPWinRate = new Label();
        playerPVEWinCnt = new Label();
        playerPVEWinRate = new Label();

        /**
         * Bind
         * This is such a dirty work.
         */

        playerRanking.textProperty().bind(player.rankingMMRProperty().asString());
        playerName.textProperty().bind(player.nameProperty());
        playerTotalMatchCnt.textProperty().bind(player.totalMatchCountProperty().asString());
        playerTotalWinCnt.textProperty().bind(player.totalWinCountProperty().asString());
        playerOverallWinRate.textProperty().bind(player.overallWinRateProperty().asString());
        playerPVPWinCnt.textProperty().bind(player.pvpWinCountProperty().asString());
        playerPVPWinRate.textProperty().bind(player.pvpWinRateProperty().asString());
        playerPVEWinCnt.textProperty().bind(player.pveWinCountProperty().asString());
        playerPVEWinRate.textProperty().bind(player.pveWinRateProperty().asString());


        init();
    }

    /**
     * Default constructor is used only for headers.
     *
     * @param theme theme
     */
    public PlayerItem(Theme theme) {
        this.theme = theme;


        playerRanking = new Label("Rank");
        playerName = new Label("Player Name");
        playerTotalMatchCnt = new Label("Match Count");
        playerTotalWinCnt = new Label("Win Count");
        playerOverallWinRate = new Label("Win Rate");
        playerPVPWinCnt = new Label("PVP Win Count");
        playerPVPWinRate = new Label("Win Rate");
        playerPVEWinCnt = new Label("PVE Win Count");
        playerPVEWinRate = new Label("Win Rate");

        Stop[] stops = new Stop[]{new Stop(0, Color.TRANSPARENT), new Stop(0.2, Color.rgb(255, 255, 255, 0.08)), new Stop(1, Color.TRANSPARENT)};
        LinearGradient coverFill = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        setBackground(new Background(new BackgroundFill(coverFill, null, null)));

        init();
    }

    public void init() {
        setCache(true);
        bindAllLabelToTheme();
        initLayout();
        initAnimation();
    }

    private void bindAllLabelToTheme() {

        playerRanking.textFillProperty().bind(theme.textFontPaintPR());
        playerName.textFillProperty().bind(theme.textFontPaintPR());
        playerTotalMatchCnt.textFillProperty().bind(theme.textFontPaintPR());
        playerTotalWinCnt.textFillProperty().bind(theme.textFontPaintPR());
        playerOverallWinRate.textFillProperty().bind(theme.textFontPaintPR());
        playerPVPWinCnt.textFillProperty().bind(theme.textFontPaintPR());
        playerPVPWinRate.textFillProperty().bind(theme.textFontPaintPR());
        playerPVEWinCnt.textFillProperty().bind(theme.textFontPaintPR());
        playerPVEWinRate.textFillProperty().bind(theme.textFontPaintPR());


        playerRanking.fontProperty().bind(theme.textFontFamilyPR());
        playerName.fontProperty().bind(theme.textFontFamilyPR());
        playerTotalMatchCnt.fontProperty().bind(theme.textFontFamilyPR());
        playerTotalWinCnt.fontProperty().bind(theme.textFontFamilyPR());
        playerOverallWinRate.fontProperty().bind(theme.textFontFamilyPR());
        playerPVPWinCnt.fontProperty().bind(theme.textFontFamilyPR());
        playerPVPWinRate.fontProperty().bind(theme.textFontFamilyPR());
        playerPVEWinCnt.fontProperty().bind(theme.textFontFamilyPR());
        playerPVEWinRate.fontProperty().bind(theme.textFontFamilyPR());
    }

    private void initLayout() {
        setMinHeight(DEFAULT_HEIGHT);
        setMaxHeight(DEFAULT_HEIGHT);

        getChildren().addAll(playerRanking, playerName, playerTotalMatchCnt, playerTotalWinCnt,
                playerPVPWinCnt, playerPVPWinRate, playerPVEWinCnt, playerPVEWinRate);


        setConstraints(playerRanking, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerName, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerTotalMatchCnt, 2, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerTotalWinCnt, 3, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerPVPWinCnt, 4, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerPVPWinRate, 5, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerPVEWinCnt, 6, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        setConstraints(playerPVEWinRate, 7, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

        playerRanking.setAlignment(Pos.CENTER);
        playerName.setAlignment(Pos.CENTER);
        playerTotalMatchCnt.setAlignment(Pos.CENTER);
        playerTotalWinCnt.setAlignment(Pos.CENTER);
        playerOverallWinRate.setAlignment(Pos.CENTER);
        playerPVPWinCnt.setAlignment(Pos.CENTER);
        playerPVPWinRate.setAlignment(Pos.CENTER);
        playerPVEWinCnt.setAlignment(Pos.CENTER);
        playerPVEWinRate.setAlignment(Pos.CENTER);

        ColumnConstraints constraints[] = new ColumnConstraints[8];
        for (int i = 0; i < 8; i++) {
            constraints[i] = new ColumnConstraints();
            getColumnConstraints().add(constraints[i]);
        }
        constraints[0].setPercentWidth(10);
        constraints[1].setPercentWidth(20);
        constraints[2].setPercentWidth(10);
        constraints[3].setPercentWidth(14);
        constraints[4].setPercentWidth(10);
        constraints[5].setPercentWidth(13);
        constraints[6].setPercentWidth(10);
        constraints[7].setPercentWidth(13);
    }

    private void initAnimation() {
        setOnMouseEntered(event -> {
            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
            ft.setFromValue(OPACITY_DEFAULT);
            ft.setToValue(OPACITY_SELECTED);
            ft.setCycleCount(1);
            Platform.runLater(ft::play);
        });

        setOnMouseExited(event -> {
            FadeTransition ft = new FadeTransition(Duration.millis(TRANS_TIME_MILLIS), outer());
            ft.setFromValue(OPACITY_SELECTED);
            ft.setToValue(OPACITY_DEFAULT);
            ft.setCycleCount(1);
            Platform.runLater(ft::play);
        });
    }

    private PlayerItem outer() {
        return this;
    }
}
