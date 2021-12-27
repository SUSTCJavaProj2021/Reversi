package com.demo.reversi.controller.basic.player;

public enum Mode {
    EASY(new EasyAIPlayer()),
    NORMAL(new NormalAIPlayer()),
    HARD(new HardAIPlayer()),
    HELL(new HellAIPlayer()),
    TEST(new TestAIPlayer());

    private final AIPlayer player;

    Mode(AIPlayer player) {
        this.player = player;
    }

    public AIPlayer getPlayer() {
        return player;
    }

    public static AIPlayer getPlayer(int pid) {
        if (pid == EASY.player.getPid()) {
            return EASY.player;
        } else if (pid == NORMAL.player.getPid()) {
            return NORMAL.player;
        } else if (pid == HARD.player.getPid()) {
            return HARD.player;
        } else if (pid == HELL.player.getPid()) {
            return HELL.player;
        } else {
            return null;
        }
    }
}
