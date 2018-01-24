package Tetris.Shapes;		//alap package-n belüli package

import Tetris.*;		//Tetris package-n belüli osztályok

import java.util.ArrayList;
import java.util.List;
import java.util.Random;	//hogy tudjam használni a random szám generálást

/**
 * A {@code Shape} osztálya, ahol az alakzat mozgatási műveletei és a random alakzatgenerálás van
 * megvalósítva.
 * 
 * @see Matrix
 */
public class Shape extends Matrix
{
	/**
	 * Az alakzat pozíciója {@link Position} objektumban.
	 * 
	 * @see Position
	 */
	protected Position position;	//Position típusú változó
	
	/**
	 * 	A {@link Random} típusú rand változó a random szám generáláshoz kell.
	 * 
	 * @see Random
	 */
	protected static Random rand = new Random();	//random szám használathoz

	/*milyen szám van az alakzat mátrixában*/
	protected int colorCode;
	
	public int getColorCode() 
	{
		return colorCode;	//visszaadja a színkódot
	}

	/**
	 * Visszaadja az {@code Shape} szélességét, ami egyenlő az oszlopainak a számával.
	 * 
	 * @return a mátrix oszlopainak a száma
	 * @see MatrixHelper
	 */
	public int getWidth()
	{
		return MatrixHelper.getColNum(this.matrix);	//elkérem az alakzat szélességét, ami az oszlopszám
	}
	
	/**
	 * Visszaadja az {@code Shape} magasságát, ami egyenlő a sorainak a számával.
	 * 
	 * @return a mátrix sorainak a száma
	 * @see MatrixHelper
	 */
	public int getHeight()
	{
		return MatrixHelper.getRowNum(this.matrix);	//elkérem az alakzat magasságát
	}
	
	/**
	 * Random {@code Shape} generálása. Az n változó jelenti a random generált számot,
	 * 1 és 7 között. Az {@code Shape} kezdő pozíciója a 0, 0 helyen lesz. Minden egyes
	 * szám 1 és 7 között egy alakzatnak felel meg (például az 1-es jelenti az
	 * I alakzatot).  
	 * 
	 * @return új aktuális alakzat
	 */
	public static Shape getRandomShape()
	{
		int  n = rand.nextInt(9) + 1;	//random szám 1-7 között
		Position p = new Position(0, 0);
		switch(n)
		{
		case 1:
			return new IShape(p);	//I alakzat
		case 2:
			return new JShape(p);	//J alakzat
		case 3:
			return new LShape(p);	//L alakzat
		case 4:
			return new OShape(p);	//O alakzat
		case 5:
			return new SShape(p);	//S alakzat
		case 6:
			return new ZShape(p);	//Z alakzat
		case 8:
			return new UShape(p);	//U alakzat
		case 9:
			return new CrossShape(p);	//Cross alakzat
		default:
			return new TShape(p);	//T alakzat
		}
	}

	//alakzat generálás az alakzat heurisztika értéke alapján
	public static Shape getHeuristicShape(Board b)
	{
		
		Position p = new Position(0, 0);
		List<Shape> shapes = new ArrayList<Shape>();
		shapes.add(new IShape(p));
		shapes.add(new JShape(p));
		shapes.add(new LShape(p));
		shapes.add(new OShape(p));
		shapes.add(new SShape(p));
		shapes.add(new ZShape(p));
		shapes.add(new UShape(p));
		shapes.add(new CrossShape(p));
		shapes.add(new TShape(p));	//van egy listám, ami az alakzatokat tartalmazza
		
		Shape bestHeuristicShape = shapes.get(0);	//kezdetben az elsőnek a legjobb a heurisztájának
		int bestHeuristicValue = HeuristicHelper.getHeuristic(b, bestHeuristicShape);
		for (int i = 1; i < shapes.size(); i++)
		{
			int heuristic = HeuristicHelper.getHeuristic(b, shapes.get(i));
			if(heuristic < bestHeuristicValue)
			{
				bestHeuristicValue = heuristic;	//most ez a legjobb heurisztika értékem
				bestHeuristicShape = shapes.get(i);
			}
			else if(heuristic == bestHeuristicValue)
			{
				//egyenlő heurisztika esetén egy random számtól függően vagy felülírok
				//vagy hagyom az alakzatot
				int random = rand.nextInt(100);
				if(random >= 50)
				{
					bestHeuristicValue = heuristic;	//most ez a legjobb heurisztika értékem
					bestHeuristicShape = shapes.get(i);
				}
			}
		}
		return bestHeuristicShape;
	}
	
	/**
	 * Az {@code Shape} megkapja a pozíciót. Ehhez meg kell hívni az ősosztály
	 * konstruktorát, és be kell állítani a pozíciót. 
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public Shape(Position position)	//pozíciót kap meg
	{
		super();	//ősosztály konstruktora
		this.position = position;
	}
	
	/**
	 * Visszaadja az aktuális {@code Shape} pozícióját.
	 * 
	 * @return az alakzat pozíciója
	 * @see Position
	 */
	public Position getPosition()	//visszaadja a pozíciót (eltolást)
	{
		return this.position;
	}
	
	/**
	 * Az {@code Shape} x koordinátájának a növelése, 
	 * ami a jobbra mozgatásnak felel meg.
	 */
	public void moveRight()	//jobbra mozgatás
	{
		this.position.incrementX();
	}

	/**
	 * Az {@code Shape} x koordinátájának a csökkentése, 
	 * ami a balra mozgatásnak felel meg.
	 */
	public void moveLeft()	//balra mozgatás
	{
		this.position.decrementX();
	}
	
	/**
	 * Az {@code Shape} y koordinátájának a növelése, 
	 * ami a lefele mozgatásnak felel meg.
	 */
	public void moveDown()	//lefele mozgatás (általános)
	{
		this.position.incrementY();		
	}
	
	/**
	 * Az {@code Shape} y koordinátájának a csökkentése, 
	 * ami a felfele mozgatásnak felel meg.
	 */
	public void moveUp()	//felfele mozgatás
	{
		this.position.decrementY();
	}
	
	/**
	 * Az {@code Shape} jobbra forgatása. Az {@code Shape} mátrixát transzponáljuk, majd
	 * a sorait felcseréljük.
	 * 
	 *  @see MatrixHelper
	 */
	public void rotateRight()	//jobbra forog
	{
		this.matrix = (MatrixHelper.reverseEachRow
				(MatrixHelper.transpose(this.matrix)));
	}
	
	/**
	 * Az {@code Shape} balra forgatása. Az {@code Shape} mátrixának a sorait felcseréljük,
	 * majd transzponáljuk.
	 * 
	 * @see MatrixHelper
	 */
	public void rotateLeft()	//balra forog
	{
		this.matrix = (MatrixHelper.transpose
				(MatrixHelper.reverseEachRow(this.matrix)));
	}
	
	/**
	 * Az {@code Shape} tükrözése. Az {@code Shape} mátrixának a sorait felcseréljük.
	 * 
	 * @see MatrixHelper
	 */
	public void mirrorize()
	{
		this.matrix = MatrixHelper.reverseEachRow(this.matrix);
	}
}

