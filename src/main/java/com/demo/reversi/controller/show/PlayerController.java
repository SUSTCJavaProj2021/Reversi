package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.player.Player;
import com.demo.reversi.controller.interfaces.PlayerLayer;
import javafx.beans.property.*;

public abstract class PlayerController implements PlayerLayer {
    protected Player player;

    @Override
    public Player get() {
        return player;
    }

    public void set(Player player) {
        this.player = player;
    }

    @Override
    public LongProperty pidProperty() {
        return player.getPidProperty();
    }

    @Override
    public StringProperty nameProperty() {
        return player.getNameProperty();
    }

    @Override
    public IntegerProperty totalMatchCountProperty() {
        return new SimpleIntegerProperty(player.totalCounter().overallCount());
    }

    @Override
    public IntegerProperty totalWinCountProperty() {
        return new SimpleIntegerProperty(player.winCounter().overallCount());
    }

    @Override
    public IntegerProperty pveWinCountProperty() {
        return new SimpleIntegerProperty(player.winCounter().AICount());
    }

    @Override
    public IntegerProperty pvpWinCountProperty() {
        return new SimpleIntegerProperty(player.winCounter().humanCount());
    }

    @Override
    public DoubleProperty overallWinRateProperty() {
        return new SimpleDoubleProperty(player.calculateWinRateOverall());
    }

    @Override
    public DoubleProperty pvpWinRateProperty() {
        return new SimpleDoubleProperty(player.calculateWinRateToHuman());
    }

    @Override
    public DoubleProperty pveWinRateProperty() {
        return new SimpleDoubleProperty(player.calculateWinRateToAI());
    }

    @Override
    public IntegerProperty rankingMMRProperty() {
        return player.getRatingProperty();
    }
}
