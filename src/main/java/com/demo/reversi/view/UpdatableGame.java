package com.demo.reversi.view;

public interface UpdatableGame extends Updatable{

    /**
     * This method is called to play a specific animation.
     * @param row row position of the source
     * @param col column position of the source
     */
    public void sourcedUpdate(int row, int col);

    /**
     * This method is called when the game ends.
     * A series of animation will be played, and possibly the GUI will block all inputs.
     */
    public void curtainCallUpdate();

}
