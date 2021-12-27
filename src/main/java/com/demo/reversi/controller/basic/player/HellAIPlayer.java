package com.demo.reversi.controller.basic.player;

import com.demo.reversi.controller.basic.game.Game;

public class HellAIPlayer extends AIPlayer {
    public HellAIPlayer() {
        super("_AI_HELL");

        this.mode = Mode.HELL;
        --playerCnt;
        pid.setValue(AI_PID_OFFSET + 3);
    }

    @Override
    public int[] nextStep(Game game) {
        return new int[0];
    }
}
