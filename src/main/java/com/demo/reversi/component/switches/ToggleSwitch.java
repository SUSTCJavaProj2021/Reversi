package com.demo.reversi.component.switches;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ToggleSwitch extends HBox {
    private final Label textLabel = new Label();

    private SimpleBooleanProperty switchedOn;



    private void init(){

    }

    public ToggleSwitch(){

    }

    public SimpleBooleanProperty getSwitchedOnProperty(){
        return switchedOn;
    }
}
