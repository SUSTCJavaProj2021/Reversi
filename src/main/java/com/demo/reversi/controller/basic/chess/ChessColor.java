package com.demo.reversi.controller.basic.chess;

public enum ChessColor {
    BLACK, WHITE, NULL;

    public static ChessColor dual(ChessColor color) {
        if (color == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
