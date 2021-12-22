package com.demo.reversi.component.statistics;

import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.themes.Theme;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PlayerItem extends GridPane {
    public static final double DEFAULT_HEIGHT = 50;

    public final Label playerName;
    public final Label playerTotalMatchCnt;
    public final Label playerTotalWinCnt;
    public final Label player
    public final Label playerOverallWinRate;
    public final Label playerPVEWinCnt;

    private final Theme theme;

    public PlayerItem(int index, PlayerLayer player, Theme theme) {
        this.theme = theme;
        setMaxHeight(DEFAULT_HEIGHT);
        /**
         * To make the items have a visual separator
         */
        if (index % 2 == 0) {
            backgroundProperty().bind(theme.frontPanePR());
        }


    }

    /**
     * Default constructor is used only for headers.
     * @param theme
     */
    public PlayerItem(Theme theme){
        this.theme = theme;
        backgroundProperty().bind(theme.frontPanePR());

    }
}
