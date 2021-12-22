package com.demo.reversi.controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface PlayerLayer {

    public StringProperty nameProperty();

    public IntegerProperty totalMatchCountProperty();

    public IntegerProperty totalWinCountProperty();

    public IntegerProperty pveWinCountProperty();

    public IntegerProperty pvpWinCountProperty();

    public DoubleProperty overallWinRateProperty();

    public DoubleProperty pvpWinRateProperty();

    public DoubleProperty pveWinRateProperty();

    public IntegerProperty rankingMMRProperty();
}
