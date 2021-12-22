package com.demo.reversi.component.statistics;

import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.themes.Theme;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PlayerItem extends GridPane {
    public static final double DEFAULT_HEIGHT = 45;

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
        if (index % 2 == 0) {
//            backgroundProperty().bind();
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


        bindToTheme();
        initLayout();
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

        bindToTheme();
        initLayout();
    }

    private void bindToTheme(){

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
        setConstraints(playerRanking,0,0,1,1, HPos.LEFT, VPos.CENTER);
        setConstraints(playerName,1,0,1,1, HPos.LEFT, VPos.CENTER);
        setConstraints(playerTotalMatchCnt,2,0,1,1, HPos.CENTER, VPos.CENTER);
        setConstraints(playerTotalWinCnt,3,0,1,1, HPos.CENTER, VPos.CENTER);
        setConstraints(playerPVPWinCnt,4,0,1,1, HPos.CENTER, VPos.CENTER);
        setConstraints(playerPVPWinRate,5,0,1,1, HPos.CENTER, VPos.CENTER);
        setConstraints(playerPVEWinCnt,6,0,1,1, HPos.CENTER, VPos.CENTER);
        setConstraints(playerPVEWinRate,7,0,1,1, HPos.CENTER, VPos.CENTER);

        ColumnConstraints constraints[] = new ColumnConstraints[8];
        for(int i = 0;i<8;i++){
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
}
