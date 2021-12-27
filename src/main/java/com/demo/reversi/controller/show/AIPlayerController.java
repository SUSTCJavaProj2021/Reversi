package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.player.Player;
import javafx.beans.property.IntegerProperty;

public class AIPlayerController extends PlayerController {
    public AIPlayerController(Player player) {
        this.player = player;
    }
}
