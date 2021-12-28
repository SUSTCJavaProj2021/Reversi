package com.demo.reversi.controller.show;

import com.demo.reversi.controller.basic.game.Board;
import com.demo.reversi.controller.interfaces.GameEditor;

import java.util.ArrayList;

public class GameEditorController extends GameController implements GameEditor {
    public GameEditorController(int rowSize, int columnSize) {
        super(new Board(rowSize, columnSize, new ArrayList<>(), new ArrayList<>()));
    }

    @Override
    public void setBrushAsPlayer1() {

    }

    @Override
    public void setBrushAsPlayer2() {

    }

    @Override
    public void setBrushAsEmptying() {

    }

    @Override
    public void setBrushAsBanning() {

    }

    @Override
    public void setBrushAsNull() {

    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void resetConfig() {

    }
}
