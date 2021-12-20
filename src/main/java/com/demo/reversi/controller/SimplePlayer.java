package com.demo.reversi.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimplePlayer implements PlayerLayer {
    private StringProperty nameProperty;

    public SimplePlayer(String name){
        nameProperty = new SimpleStringProperty(name);
    }

    public StringProperty nameProperty(){
        return nameProperty;
    }
}
