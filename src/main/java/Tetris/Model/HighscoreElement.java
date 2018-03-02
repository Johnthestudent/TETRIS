package Tetris.Model;


import java.time.LocalDateTime;

import Tetris.Difficulty;

/**
 * A {@code HighscoreElement} osztálya, mely a modellt definiálja.
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
	private LocalDateTime achieved;		
	
	/**
	 * A játék nehézségi szintje.
	 */
	private Difficulty difficulty;
	
	/**
	 * Konstruktor a játékos nevének, pontszámának, dátumnak és a játék nehézség beállításához.
	 * @param playername a játékos neve
	 * @param playerscore a játékos pontszáma
	 * @param achieved a játékos ekkor érte el a pontszámot
	 * @param difficulty a játék nehézségi szintje
	 */
	public HighscoreElement(String playername, int playerscore, LocalDateTime achieved, Difficulty difficulty)
	{
		this.playername = playername;
		this.playerscore = playerscore;
		this.achieved = achieved;
		this.difficulty = difficulty;
	}
	
	/**
	 * Visszaadja a játék nehézségi szintjét.
	 * 
	 * @return a játék nehézségi szintje
	 */
	public Difficulty getDifficulty() 
	{
		return difficulty;
	}

	/**
	 * Beállítja a játék nehézségi szintjét.
	 * 
	 * @param difficulty a játék nehézségi szintje
	 */
	public void setDifficulty(Difficulty difficulty) 
	{
		this.difficulty = difficulty;
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
	 * @see LocalDateTime
	 */
	public LocalDateTime getAchieved() 
	{
		return achieved;
	}

	/**
	 * Beállítja azt az időpontot, mint dátumot, amikor a játékos elérte
	 * a pontszámot.
	 * 
	 * @param achieved a dátum
	 * @see LocalDateTime
	 */
	public void setAchieved(LocalDateTime achieved) 
	{
		this.achieved = achieved;
	}
}
