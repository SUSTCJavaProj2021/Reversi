package com.demo.reversi.component.switches;

import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ToggleSwitch extends HBox implements Updatable {
    private final double DEFAULT_WIDTH = 60;
    private final double DEFAULT_HEIGHT = 30;
    private final double CORNER_RADII = 15;
    private final double INDICATOR_RADII = 10;

    private final Circle indicator;


    private SimpleBooleanProperty switchedOn;
    public SimpleBooleanProperty switchedOnProperty(){
        return switchedOn;
    }

    private Theme theme;

    private void init(){
        switchedOn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

            }
        });
        switchedOn.setValue(false);
    }

    public void update(){
        updateIndicator();
    }

    private void updateIndicator(){
        if(switchedOn.getValue()){

        }
        else{

        }
    }

    public ToggleSwitch(Theme theme){
        this.theme = theme;
        indicator = new Circle(INDICATOR_RADII);
        init();
    }

}
