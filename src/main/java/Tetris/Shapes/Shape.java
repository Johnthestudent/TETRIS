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

	/**
	 * az alakzathoz tartozó fix heurisztika érték nehéz játék módban.
	 */
	protected int hardheuristicValue;
	
	/**
	 * Visszaadja az alakzathoz tartozó heurisztika értéket.
	 * 
	 * @return az alakzathoz tartozó heurisztika érték nehéz játékszint esetén
	 */
	public int getHardheuristicValue() 
	{
		return hardheuristicValue;
	}

	/**
	 * Visszaadja az alakzathoz tartozó heurisztika értéket.
	 * 
	 * @return az alakzathoz tartozó heurisztika érték könnyű játékszint esetén
	 */
	public int getEasyheuristicValue() 
	{
		return easyheuristicValue;
	}

	/**
	 * Az alakazat heurisztika értéke könnyű nehézségi módban.
	 */
	protected int easyheuristicValue;

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
	 * 1 és 9 között. Az {@code Shape} kezdő pozíciója a 0, 0 helyen lesz. Minden egyes
	 * szám 1 és 9 között egy alakzatnak felel meg (például az 1-es jelenti az
	 * I alakzatot).  
	 * 
	 * @return új aktuális alakzat
	 */
	public static Shape getRandomShape()
	{
		int  n = rand.nextInt(9) + 1;	//random szám 1-9 között
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

	/**
	 * Alakzat ajánló algoritmus. Ez a metódus akkor hívódik meg, ha könnyű vagy nehéz játék módban
	 * van a játék. Könnyű módban a fix heurisztika értékek felcserélődnek egy tömbben (1-ből 4 lesz,
	 * 2-ből 3). Annyi eleme lesz az említett tömbnek, ahány alakzat van. Utána felülíródnak az elemek
	 * úgy, hogy összeadódnak, így pl. 1, 3, 5 esetén az új elemek 1, 4, 9 lesz. Ezután generálódik egy
	 * random szám. Az algoritmus megvizsgálja a tömb elemeit páronként, és megnézi, hogy melyikhez van
	 * közelebb a generált szám. Pl. generált szám 9 és a 8-12 számpárból a 8-hoz van közelebb, így az
	 * ahhoz tartozó, adott heurisztika értékű alakzatot fogja ajánlani és generálni az algoritmus.
	 * Ugyanez megy nehéz játékmódban is, azzal a különbséggel, hogy a heurisztika értékek adódnak össze,
	 * és így töltődik fel az említett tömb.
	 * 
	 * @param d a játék nehézségi szintje
	 * @return az ajánlott alakzat
	 */
	public static Shape getHeuristicShape(Difficulty d)
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
		
		if(shapes.size() == 1)
		{
			return shapes.get(0);	//ha csak egy alakzatom van
		}
		
		int[] heuristicArray = new int[shapes.size()];	//tömb a heurisztika számoláshoz
		
		//könnyű vagy nehéz nehézségtől függően adódik hozzá a heurisztika érték
		if(d == Difficulty.EASY)
		{
			heuristicArray[0] = shapes.get(0).getEasyheuristicValue();
		}
		else
		{
			heuristicArray[0] = shapes.get(0).getHardheuristicValue();
		}
		
		for(int j = 1; j < shapes.size(); j++)
		{
			if(d == Difficulty.EASY)
			{
				heuristicArray[j] = heuristicArray[j - 1] + shapes.get(j).getEasyheuristicValue();
			}
			else
			{
				heuristicArray[j] = heuristicArray[j - 1] + shapes.get(j).getHardheuristicValue();
			}
	 
		}
		
		//most generálódik random szám a tartományból
		int  m = rand.nextInt(heuristicArray[heuristicArray.length-1]) + heuristicArray[0];
		//aktuális értéket nézem, meg a következőt
		for(int k = 0; k < heuristicArray.length - 1; k++)
		{
			//ha megtaláltam azt a helyet, amelyik két szám között van a generált szám
			if(m >= heuristicArray[k] && m <= heuristicArray[k + 1])
			{
				//azt az alakzatot ajánlom ki, aminél a generált random szám közelebb van a
				//heurisztika táblázat értékhez
				if((m - heuristicArray[k]) < (heuristicArray[k + 1] - m))
				{
					return shapes.get(k);
				}
				else
				{
					return shapes.get(k + 1);
				}
			}
		}
		
		return shapes.get(0);
		
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

