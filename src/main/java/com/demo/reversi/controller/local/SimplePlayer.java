package com.demo.reversi.controller.local;

import com.demo.reversi.controller.interfaces.PlayerLayer;
import javafx.beans.property.*;

public class SimplePlayer implements PlayerLayer {
    private StringProperty nameProperty;

    public SimplePlayer(String name){
        nameProperty = new SimpleStringProperty(name);
    }

    @Override
    public LongProperty pidProperty(){return new SimpleLongProperty(2333333);}

    @Override
    public StringProperty nameProperty(){
        return nameProperty;
    }

    @Override
    public IntegerProperty totalMatchCountProperty() {
        return new SimpleIntegerProperty(233);
    }

    @Override
    public IntegerProperty totalWinCountProperty() {
        return new SimpleIntegerProperty(124);
    }

    @Override
    public DoubleProperty overallWinRateProperty() {
        return new SimpleDoubleProperty(0.5321);
    }

    @Override
    public IntegerProperty pvpWinCountProperty() {
        return new SimpleIntegerProperty(106);
    }

    @Override
    public DoubleProperty pvpWinRateProperty() {
        return new SimpleDoubleProperty(0.629);
    }

    @Override
    public IntegerProperty pveWinCountProperty() {
        return new SimpleIntegerProperty(18);
    }

    @Override
    public DoubleProperty pveWinRateProperty(){
        return new SimpleDoubleProperty(0.5);
    }

    @Override
    public IntegerProperty rankingMMRProperty() {
        return new SimpleIntegerProperty(2810);
    }
}
