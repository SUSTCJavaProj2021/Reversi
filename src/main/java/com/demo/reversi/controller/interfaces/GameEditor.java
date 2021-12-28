package com.demo.reversi.controller.interfaces;

public interface GameEditor extends GameControllerLayer{

    public void setBrushAsPlayer1();

    public void setBrushAsPlayer2();

    /**
     * If invoked, any onGridClick should restore the corresponding grid
     * to its default status.
     */
    public void setBrushAsEmptying();

    public void setBrushAsBanning();

    public void setBrushAsNull();

    public void saveConfig();

    public void resetConfig();

}
