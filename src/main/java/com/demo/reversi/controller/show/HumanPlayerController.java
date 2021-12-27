package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.player.HumanPlayer;
import com.demo.reversi.controller.basic.player.Player;
import javafx.beans.property.*;

public class HumanPlayerController extends PlayerController {
    public HumanPlayerController(String name) {
        player = new HumanPlayer(name);
    }

    public HumanPlayerController(Player player) {
        this.player = player;
    }
}
