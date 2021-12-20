package com.demo.reversi.component.panes;

import com.demo.reversi.controller.PlayerLayer;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.beans.property.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

public class InfoPane extends GridPane implements Updatable {
    public static final double PREF_HEIGHT = 60;
    public static final double PREF_WIDTH = 150;
    public static final double OPACITY_ACTIVATED = 1.0;
    public static final double OPACITY_DEFAULT = 0.8;

    public final Label playerNameLabel;
    public final ObjectProperty<Image> playerIcon;
    public final ObjectProperty<Paint> playerColorPR;
    public final BooleanProperty isActivated;

    public BooleanProperty isActivatedProperty(){
        return isActivated;
    }

    private final Theme theme;

    public InfoPane(PlayerLayer player, Theme theme, ObjectProperty<Paint> playerColor){
        this.theme = theme;
        playerColorPR = new SimpleObjectProperty<>();
        playerColorPR.bind(playerColor);
        playerNameLabel = new Label();
        playerNameLabel.textProperty().bind(player.nameProperty());
        playerNameLabel.fontProperty().bind(theme.infoTitleFontFamilyPR());
        playerNameLabel.textFillProperty().bind(theme.infoTitleFontPaintPR());
        isActivated = new SimpleBooleanProperty(false);
        playerIcon = new SimpleObjectProperty<>();


        //For test
        playerIcon.setValue(Theme.defaultPlayerIcon);
    }

    @Override
    public void update(){

    }

}
