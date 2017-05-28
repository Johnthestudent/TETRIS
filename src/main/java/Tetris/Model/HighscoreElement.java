package Tetris.Model;

import java.util.Date;

/**
 * A {@code HighscoreElement} osztálya, mely ezeket tartalmazza: játékos neve, pontszáma, mikor érte
 * el a pontszámot, konstruktorok az adatok beállításához és inicializáláshoz, játékosnév/pontszám 
 * illetve dátum érték visszaadás/beállítás.
 */
public class HighscoreElement 
{
	/**
	 * A játékos neve.
	 */
	private String playername;
	
	/**
	 * A játékos pontszáma.
	 */
	private int playerscore;
	
	/**
	 * A játékos ekkor érte el a pontszámot.
	 */
	private Date achieved;		
	
	/**
	 * Konstruktor a játékos nevének, pontszámának és a dátumnak beállításához.
	 * @param playername a játékos neve
	 * @param playerscore a játékos pontszáma
	 * @param achieved a játékos ekkor érte el a pontszámot
	 */
	public HighscoreElement(String playername, int playerscore, Date achieved)
	{
		this.playername = playername;
		this.playerscore = playerscore;
		this.achieved = achieved;
	}
	
	/**
	 * Visszaadja a játékos nevét.
	 * 
	 * @return a játékos neve
	 */
	public String getPlayername() 
	{
		return playername;
	}

	/**
	 * Beállítja a játékos nevét.
	 * 
	 * @param playername a játékos neve
	 */
	public void setPlayername(String playername) 
	{
		this.playername = playername;
	}

	/**
	 * Visszaadja a játékos pontszámát.
	 * 
	 * @return a játékos pontszáma
	 */
	public int getPlayerscore() 
	{
		return playerscore;
	}

	/**
	 * Beállítja a játékos pontszámát
	 * 
	 * @param playerscore a játékos pontszáma
	 */
	public void setPlayerscore(int playerscore) 
	{
		this.playerscore = playerscore;
	}

	/**
	 * Visszaadja azt az időpontot, mint dátumot, amikor a játékos elérte 
	 * a pontszámot.
	 * 
	 * @return a dátum
	 * @see Date
	 */
	public Date getAchieved() 
	{
		return achieved;
	}

	/**
	 * Beállítja azt az időpontot, mint dátumot, amikor a játékos elérte
	 * a pontszámot.
	 * 
	 * @param achieved a dátum
	 * @see Date
	 */
	public void setAchieved(Date achieved) 
	{
		this.achieved = achieved;
	}
}
