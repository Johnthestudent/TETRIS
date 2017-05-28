package Tetris.Model;

import java.util.*;

/**
 * A {@code HighscoreModel} osztálya, mely ezeket tartalmazza: vektor, konstruktor, eredményeket
 * tartalmazó tábla visszaadása/beállítása
 */
public class HighscoreModel 
{	
	/**
	 * {@link HighscoreElement} objektumokat tartalmazó ponttábla megfeleltetése, mint vektor.
	 */
	private Vector<HighscoreElement> highscoreTable = new Vector<HighscoreElement>();
	
	/**
	 * Konstruktor az objektum létrehozásához.
	 */
	public HighscoreModel(){};
	
	/**
	 * Visszaadja a játékosok eredményeit tartalmazó táblát.
	 * 
	 * @return a játékosok eredményeit tartalmazó tábla
	 * @see HighscoreElement
	 */
	public Vector<HighscoreElement> getHighscoreTable() 
	{
		return highscoreTable;
	}

	/**
	 * Beállítja a játékosok eredményeit tartalmazó táblát.
	 * 
	 * @param highscoreTable a játékosok eredményeit tartalmazó tábla
	 * @see HighscoreElement
	 */
	public void setHighscoreTable(Vector<HighscoreElement> highscoreTable) 
	{
		this.highscoreTable = highscoreTable;
	}
}