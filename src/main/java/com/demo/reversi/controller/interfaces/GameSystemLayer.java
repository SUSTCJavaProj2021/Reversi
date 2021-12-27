package com.demo.reversi.controller.interfaces;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * GameSystem Layer defines a set of APIs that guides how the actual game interact with the GameSystem object.
 *
 * The GameSystem will first be instantiated in the MainApp application, and be passed to all these pages that need the GameSystem.
 * The GameSystem shall itself load ALL save files from the save folder by calling the method <code>SaveLoader.class.getResource()</code>
 * and the method <code>Paths.get(Paths.get(SaveLoader.class.getResource(pathToResource).toURI.toString), nameOfTheResource)</code>
 * Example can be found in the theme file, in which default themes are loaded through hard-coded instructions.
 *
 * All the games can be indexed according your specific rules.
 *
 * The reason that a static class is not used here is because that these static classes may iterate many times and thus cause too much
 * maintenance in the GUI part.
 *
 * Overall, the GameSystem which implements this interface shall perform all the required operations to finish its initialization in its
 * default constructor, which at the SimpleGameSystem is a default constructor that does nothing, because it should be merely manipulates
 * the static class. However, in your constructor, you may need to initialize and load the default GameSystem if needed.
 *
 * After the instantiation (constructor has been called) of the GameSystem, any of the methods in this interface can (and possibly will)
 * be called by GUI.
 *
 */
public interface GameSystemLayer {

    /**
     * Return all the games that are related to the specific player.
     * @param player the specific player
     */
    public List<GameControllerLayer> queryPlayerGames(PlayerLayer player);

    /**
     * Require the GameSystem to add a new Player
     *
     * Names cannot duplicate for simplicity, or selecting a player instance in the
     * GUI would be really, really painful.
     *
     * @return the player instance.
     */
    public PlayerLayer createNewPlayer(String PlayerName);

    /**
     * As the name suggests.
     * @param playerName player name as the identifier
     * @return the player object
     */
    public PlayerLayer getPlayer(String playerName);

    /**
     * Require the GameSystem to delete a specific player by its instance.
     * The implementation could be that PID is used as the actual judge.
     * @return <code>true</code> if the operation succeeds
     */
    public boolean delPlayer(PlayerLayer player);

    /**
     * Require the GameSystem to delete a specific player by its name.
     * @param playerName playerName
     * @return <code>true</code> if the operation succeeds
     */
    public boolean delPlayer(String playerName);

    /**
     * Call the GameSystem to start a new game with specific players
     * @param playerName1 Player 1
     * @param playerName2 Player 2
     * @return The instance of the specific game controller
     */
    public GameControllerLayer startNewGame(String playerName1, String playerName2);

    /**
     * Call the GameSystem to start a new game with desired board size.
     * @param playerName1 Player 1
     * @param playerName2 Player 2
     * @param rowSize Row size
     * @param colSize Column size
     * @return
     */
    public GameControllerLayer startNewGame(String playerName1, String playerName2, int rowSize, int colSize);

    /**
     * Load the Game from the specific path.
     * @param file  File source.
     */
    public GameControllerLayer loadGame(File file);

    /**
     * Get all the players in sorted order according to MMR (from higher to lower)
     * @return List of players in sorted order (according to MMR)
     */
    public List<PlayerLayer> queryPlayerInfoAllSorted();

    /**
     * Get all the game controllers in unmodifiable mode. (Which means it doesn't respond to onGridClick.)
     * This is used for demonstrate the game status only.
     * @return List of game controllers in chronological order (starting from now)
     */
    public List<GameControllerLayer> queryGameControllerAllSorted();

    /**
     * Make the game controller playable.
     * @param controller The controller handle
     * @return the original controller
     */
    public GameControllerLayer registerGamePlayable(GameControllerLayer controller);

    public GameControllerLayer unregisterGamePlayable(GameControllerLayer controller);

    /**
     * Load the GameSystem instance from the selected file.
     * @param file The GameSystem File
     * @return <code>true</code> if the operation succeeds
     */
    public boolean load(File file);

    /**
     * Save the current state of the GameSystem instance
     * @return <code>true</code> if the operation succeeds
     */
    public boolean save();


    /**
     * Save the current state of the GameSystem instance to the file.
     * @param file the file object to which you should save
     * @return <code>true</code> if the operation succeeds
     */
    public boolean saveTo(File file);

    /**
     * Reset the GameSystem. Including all settings and user data.
     * @return <code>true</code> if the operation succeeds
     */
    public void reset();
}
