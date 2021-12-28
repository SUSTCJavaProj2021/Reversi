package com.demo.reversi.controller.interfaces;

import com.demo.reversi.view.UpdatableGame;
import javafx.concurrent.Task;

import java.io.File;
import java.time.LocalDateTime;

/**
 * GameControllerLayer is the interface through which GUI gets all the necessary infos about a game.
 * GameControllerLayer responds to method onGridClick mainly, and should call the specific GUI update
 * when necessary.
 *
 * The full game lifecycle is described as follows:
 *
 * 1. Initialization.
 * During the startup period of the game platform, it will try to display the game using GamePreviewPane.
 * The pane will load a read-only GameController to get the status of the game and display the rendered
 * ChessBoard to the player.
 *
 * 2. Loading
 * In the play page, either a new GameController will be created or an existing GameController will be
 * required to be reloaded to be playable.
 *
 * 3. Save
 * After the game finished or actively exited, GameController will be called to save its current state,
 * but the GameController still needs to be maintained for Preview purpose.
 *
 *
 */
public interface GameControllerLayer {

    /**
     * Bind to the specific GamePage so that later it can call GUI update.
     * Notice that before this method is called, the GamePage may not exist.
     *
     * A ChessBoard, when in demo mode, only bind to the controller in a unidirectional way so that it
     * can update its chess grids, and the onGridClick method will never be called, and the controller
     * will not be able to call GUI ChessBoard update.
     *
     *
     * @param gamePage Updatable GamePage
     */
    public void bindToGamePage(UpdatableGame gamePage);


    /**
     * This will be called when the GamePage is closed or thrown away.
     */
    public void unbindGamePage();


//    public boolean bindToChessBoard();


    public int getRowSize();


    public int getColSize();


    /**
     * Respond to GUI onGridClick event.
     * I haven't checked this much, but you should decide whether the actual game
     * should be modified according to this event
     *
     * CAUTION: normally you should judge the game after the final onGridClick after
     * which none of the players can make a valid move.
     * In that case, you should create a new task and wait 1000 ms to call GUI update,
     * because there might be ongoing animation event.
     * The specific example can be found in SimpleGameController.
     *
     * @param row row position of the click event
     * @param col column position of the click event
     * @return
     */
    public void onGridClick(int row, int col);

    /**
     * This is method is called when the current player abandons his chance
     * to place a chess.
     */
    public void forcePause();


    /**
     * When called, the controller should replay the game from start itself.
     *
     * Suggestion: Simply call onGridClick for each 500 ms in this method. Or you will
     * call forcedGUIUpdate each time.
     */
    public void replayGame();


    /**
     * Call the GUI to update its contents, including ChessBoard (without directed animation, but
     * basic animation still remains) and Current Player Indicator. (Currently. In the future may
     * be more contents will be updated.)
     */
    public void forceGUIUpdate();


    public void forceGUIUpdate(Task<?> task);


    /**
     * Call the GUI to update its contents, and in addition, play an animation on the ChessBoard
     * (with directed animation.)
     * Normally, this method should be called when onGridClick is triggered, to create an animation.
     * @param row the column position of the source
     * @param col the column position of the source
     */
    public void forceSourcedGUIUpdate(int row, int col);


    /**
     * The only difference between this method and the former one is that it adds a task that is going
     * to be executed after the GUI update. Therefore, no more <code>Thread.sleep()</code> is needed.
     * @param row the column position of the source
     * @param col the column position of the source
     * @param task the task that is going to be executed after the GUI update
     */
    public void forceSourcedGUIUpdate(int row, int col, Task<?> task);


    /**
     * Call this when the game is finished.
     * In the method, you should call Updatable::curtainCallUpdate
     * The specific method can be found in SimpleGameController
     */
    public void curtainCallUpdate();


    /**
     * Reset the CLI game.
     * No GUI calling is required in this method, because the ChessBoard will be redrawn.
     */
    public void restartGame();


    /**
     * Return the created time.
     * @return created time
     */
    public LocalDateTime getGameCreatedTime();


    /**
     * Return the last modified time.
     * @return last modified time
     */
    public LocalDateTime getGameLastModifiedTime();


    /**
     * Return the status of the game instance.
     * @return the status of the current game
     */
    public GameStatus getGameStatus();


    /**
     * Get Player 1 (namely, the black player.)
     * The reason why "BlackPlayer" is not used is because that custom player color is allowed.
     * @return Player 1
     */
    public PlayerLayer getPlayer1();


    /**
     * Get Player 2 (namely, the white player.)
     * @return Player 2
     */
    public PlayerLayer getPlayer2();


    /**
     * Get the current player
     * @return the current player who is in turn but hasn't placed his chess yet.
     */
    public PlayerLayer getCurrentPlayer();

    /**
     *
     * @return <code>true</code> if cheat mode is enabled.
     */
    public boolean isCheatMode();


    /**
     * A toggle switch will be used to toggle the cheat mode.
     * @param isEnabled <code>true</code> if the attempt tries to enable the cheat mode
     * @return <code>true</code> if the operation succeeds.
     */
    public void setCheatMode(boolean isEnabled);


    /**
     * Change the player that the cheater want to play as.
     * @param isPlayer1 If <code>true</code>, then the next step should be placed as Player1's step.
     *                  Validity check should not be performed, and you should not make a turn.
     *                  However, you should still flip chess according to the rule.
     * @return <code>true</code> If the operation succeeds.
     */
    public void forceSideSwapping();


    /**
     * Query the controller if the last step can be undone.
     * @return <code>true</code> If the player can undo the last step made.
     */
    public boolean isUndoAvailable();


    /**
     * Undo the last step made.
     * @return <code>true</code> If the undo process succeeds.
     */
    public boolean undoLastStep();


    /**
     *
     * @return <code>true</code> If Player 1 is AI.
     */
    public boolean isPlayer1AI();


    /**
     *
     * @return <code>true</code> If Player 2 is AI.
     */
    public boolean isPlayer2AI();

    public boolean performAINextStep();


    /**
     * Get the status of the specific grid.
     * This method will be called frequently to iterate through all the available grids.
     * @param row row position required
     * @param col column position required
     * @return the status of the grid.
     */
    public GridStatus getGridStatus(int row, int col);


    /**
     * This will be called when the GUI tries to save the current status of the game to the GameSystem.
     * In general, this method is not used yet, but you should try to save the game in your default directory.
     * The default directory is under save/games/
     * @return <code>true</code> if the operation succeeds.
     */
    public boolean save();


    /**
     * Save the status of the current game to a specific file.
     * @param file handle of the file
     * @return <code>true</code> if the operation succeeds.
     */
    public boolean saveTo(File file);

    public void setReadOnly(boolean isReadOnly);
}
