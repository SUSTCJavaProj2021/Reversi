package com.demo.reversi.controller.interfaces;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

/**
 * For real-time preview, you should create a property for these attributes, and each time you want to modify them,
 * you should call property.setValue(T valueT). To get the value, you should call property.getValue();
 *
 * However, if you don't want to maintain the properties, just new a property and return it to the caller. However,
 * this is not suggested unless you really don't have time.
 */
public interface PlayerLayer {

    public LongProperty pidProperty();

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
