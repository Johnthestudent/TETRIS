package Tetris;

import java.util.ArrayList;
import java.util.List;

import Tetris.Shapes.Board;
import Tetris.Shapes.Shape;

import javafx.beans.property.*;

import org.pmw.tinylog.Logger; 

/**
 * A {@code Game} osztálya, ahol a játék kerül megvalósításra.
 */
public class Game 
{
	/**
	 * Ez a játékon belül a pálya.
	 * 
	 * @see Board
	 */
	protected Board board;	
	
	/**
	 * Ez az aktuális alakzat a pályán.
	 * 
	 * @see Shape
	 */
	protected Shape currentshape;
	
	/**
	 * Ez a pontszám. Kötésre alkalmas.
	 * 
	 * @see SimpleIntegerProperty
	 */
	protected SimpleIntegerProperty points;
	
	/**
	 * Visszaadja a pontok számát. Kötésre alkalmas.
	 * 
	 * @return a megváltozott pontszám
	 * @see SimpleIntegerProperty
	 */
	public SimpleIntegerProperty getPoints() 
	{
		return points;
	}
	
	/**
	 * Ez adja meg azt, hogy le van-e állítva a játék.
	 */
	protected boolean isPaused = false;
	
	/**
	 * Ez adja meg azt, hogy ki játszik most. Kötésre alkalmas.
	 * 
	 * @see SimpleStringProperty
	 */
	protected SimpleStringProperty playerName;
	
	/**
	 * Az eseménykezelő feliratkozottak listája.
	 * 
	 * @see GameEventListener
	 */
    private List<GameEventListener> listeners = new ArrayList<GameEventListener>();

    /**
     * A játékesemény kezelőket hozzáaadja a listához.
     * 
     * @param toAdd egy játékesemény kezelő
     */
    public void addListener(GameEventListener toAdd) 
    {
    	listeners.add(toAdd);
    }
    
    private Difficulty gameDifficulty;	//játék nehézsége

    public Difficulty getGameDifficulty() 
    {
		return gameDifficulty;
	}

	public void setGameDifficulty(Difficulty gameDifficulty) 
	{
		this.gameDifficulty = gameDifficulty;
	}

	/**
     * {@code Game} indítása. A {@code Game} kezdetekor a pontszámot kinullázza, inicializálja 
     * a pályát és új random alakzatot generál.
     * 
     * @param width a pálya szélessége
     * @param height a pálya magassága
     * @param gameDifficulty a játék nehézségi szintje
     */
	public Game(int width, int height, Difficulty gameDifficulty)
	{
		this.gameDifficulty = gameDifficulty;
		this.points = new SimpleIntegerProperty();	//kezdő pontszám
		this.points.set(0);		//kezdeti pontszám nulla
		this.playerName=new SimpleStringProperty("");
		this.board = new Board(width, height);	//létrejött a pálya
		shapeReset();	//jön az új alakzat
		Logger.info("Elindult a játék!");
	}
	
	/**
	 * A {@code Game} leállítása. Ez a P billentyűvel történik. A billentyű újbóli 
	 * lenyomására folytatódik a {@code Game}.
	 */
	public void togglePause()
	{
		this.isPaused = !isPaused;	//ha true, akkor false, ha false, akkor true
		if(this.isPaused)
		{
			Logger.info("Szünetel a játék!");
		}
		else
		{
			Logger.info("Megy tovább a játék!");
		}
	}
	
	/** 
	 * Visszaadja, hogy le van-e állítva a játék.
	 * 
	 * @return a leállított játék
	 */
	public boolean getPaused()
	{
		return this.isPaused;
	}

	/**
	 * Új {@link Shape} generálása a pályán, felül középen. Azonnali ütközés esetén game over esemény
	 * generálása történik.
	 */
	private void shapeReset()
	{
		this.currentshape = Shape.getRandomShape();	//random shapet fog visszaadni
		this.currentshape.getPosition().setX(this.board.getWidth()/2 - this.currentshape.getWidth()/2);
		if(this.board.hasCollision(this.currentshape) == true)	//ha ütközök
		{
			//game over esemény
			for (GameEventListener l : listeners)
			{
				l.gameOver(this.playerName.get(),this.points.get());
			}
			Logger.info("Game over!");
			this.points.set(0);	//pontszám kinullázása
			this.board.initBoard();	//pálya újragenerálása            
		}
		else
		{
			Logger.info("Új alakzatot generált!");
		}
	}

	/**
	 * Visszaadja a {@code Game} pályáját.
	 * 
	 * @return a {@link Board} típusú pálya
	 * @see Board
	 */
	public Board getBoard() 
	{
		return board;
	}

	/**
	 * Visszaadja a {@code Game} aktuális alakzatát.
	 * 
	 * @return a {@link Shape} típusú aktuális alakzat
	 * @see Shape
	 */
	public Shape getCurrentshape() 
	{
		return currentshape;
	}

	/**
	 * Az aktuális {@link Shape} jobbra mozgatásának művelete. Jobb oldali ütközés 
	 * esetén nem hajtódik végre. 
	 */
	public void moveShapeRight()
	{
		this.currentshape.moveRight();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.moveLeft(); //visszatolom az alakzatot
		}
	}

	/**
	 * Az aktuális {@link Shape} balra mozgatásának művelete. Bal oldali ütközés esetén
	 * nem hajtódik végre. 
	 */
	public void moveShapeLeft()	
	{
		this.currentshape.moveLeft();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.moveRight(); //visszatolom az alakzatot
		}
	}

	/**
	 * Az aktuális {@link Shape} lefele mozgatásának művelete. Ha ütközik a
	 * {@link Board} aljával vagy egy a pályán már lehelyezett alakzattal, akkor
	 * lementődik a pályán, és generál egy új alakzatot.
	 */
	public void moveShapeDown()	
	{
		this.currentshape.moveDown();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.moveUp(); //visszatolom az alakzatot
			this.board.saveShape(this.currentshape);	//rámásolom az alakzat mátrixát a pályára
			this.points.set(this.points.getValue()+ this.board.boardSweep());
			shapeReset();	//új alakzat jön
		}
	}
	
	/**
	 * Az aktuális {@link Shape} balra forgatása. Ha bal oldalon ütközés van, akkor
	 * nem hajtódik végre.
	 */
	public void rotateShapeLeft()
	{
		this.currentshape.rotateLeft();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.rotateRight(); //visszaforgatom
		}
	}
	
	/**
	 * Az aktuális {@link Shape} jobb forgatása. Ha jobb oldalon ütközés van, akkor 
	 * nem hajtódik végre.
	 */
	public void rotateShapeRight()
	{
		this.currentshape.rotateRight();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.rotateLeft();
		}
	}
	
	/**
	 * Az aktuális {@link Shape} tükrözése. Ha egy adott oldalon ütközés van,
	 * akkor nem hajtódik végre.
	 */
	public void mirrorShape()
	{
		this.currentshape.mirrorize();
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.mirrorize();
		}
	}
	
	/**
	 * Az aktuális {@link Shape} leejtése. Addig mozgatja lefele, amíg nem ütközik
	 * a pálya aljával. 
	 */
	public void dropShape()
	{
		while(!this.board.hasCollision(this.currentshape))
		{
			this.currentshape.moveDown();
		}
		if(this.board.hasCollision(this.currentshape) == true)
		{
			this.currentshape.moveUp();
		}
	}
	
	/**
	 * Új {@code Game} indítása, pontszám kinullázása, pálya ürítése, új alakzat generálása.
	 */
	public void resetGame()
	{
		this.points.set(0);		//0 ponttal indulok
		this.board.initBoard(); //pálya mátrixának kinullázása
		this.shapeReset();		//kérek egy új random alakzatot
		this.isPaused = false;	
		Logger.info("Új játék indult!");
	}
	
	/**
	 * Visszaadja az aktuális {@code Game} játékosának nevét. Kötésre alkalmas.
	 * 
	 * @return az aktuális {@code Game} játékosának neve
	 * @see SimpleStringProperty
	 */
	public SimpleStringProperty getPlayername() 
	{
		return playerName;
	}
	
	/**
	 * Beállítja az aktuális {@code Game} játékosának nevét
	 * 
	 * @param playername az aktuális {@code Game} játékosának neve
	 */
	public void setPlayername(String playername) 
	{
		this.playerName.set(playername);
	}
}
