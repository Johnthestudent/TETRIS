package Tetris;

import javafx.beans.property.*;

/**
 * Játékbeli esemény kezelő, a game over egy játékbeli esemény.
 */
public interface GameEventListener 
{
	void gameOver(String player, int points);
}
