package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
    public StringProperty nameProperty;

    public Player(){
        nameProperty = new SimpleStringProperty();
    }

    public Player(String playerName){
        nameProperty = new SimpleStringProperty(playerName);
    }
}
