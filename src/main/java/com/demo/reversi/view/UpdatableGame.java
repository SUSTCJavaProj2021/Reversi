package com.demo.reversi.view;

public interface UpdatableGame extends Updatable{
    public void sourcedUpdate(int row, int col);
    public void curtainCallUpdate();
}
