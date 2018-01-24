package Tetris.Shapes;

import Tetris.*;

/**
 * A {@code Board} osztálya, ahol a pálya megvalósítása, az ütközésvizsgálat, a lehelyezett alakzat
 * elmentésének megvalósítása és a teli sor eltüntetésének megvalósítása van.
 * 
 * @see Shape
 */
public class Board extends Matrix
{
	/**
	 * Ez tárolja a pálya szélességét.
	 */
	private int width;
	
	/**
	 * Ez tárolja a pálya magasságát.
	 */
	private int height;

	/**
	 * A {@code Board} mátrixának elkészítése. 12*20-as méretű mátrix lesz.
	 * 
	 * @see Matrix
	 */
	public Board()
	{
		super();	//Matrix ősosztály konstruktora
		this.width = 12;
		this.height = 20;
		this.matrix = new int[this.width][this.height];	//tényleges mátrix elkészítése
		initBoard();
	}
	
	/**
	 * Konstruktor, mely egy megadott szélességű és magasságú {@code Board} objektumot hoz létre. 
	 * 
	 * @param width a pálya szélessége
	 * @param height a pálya magassága
	 */
	public Board(int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
		this.matrix = new int[height][width];
		initBoard();
	}
	
	/**
	 * Visszaadja a {@code Board} szélességét.
	 * 
	 * @return a {@code Board} szélessége
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * A {@code Board} inicializálása. Itt lesz a mátrix összes eleme nulla. 
	 * Új játék megkezdése előtt ezen mátrix minden elemét ki kell nullázni. 
	 * 
	 * @see MatrixHelper
	 */
	public void initBoard()
	{
		for(int i = 0; i < MatrixHelper.getRowNum(this.matrix); i++)
		  {
			  	for(int j = 0; j < MatrixHelper.getColNum(this.matrix); j++)
				{
					this.matrix[i][j] = 0;		//kinullázása a pályának (alaphelyzetbe hozás)
				}				
		  }
	}
	
	/**
	 * Az {@link Shape} ütközésének a vizsgálata. A vizsgálathoz szükség van az {@link Shape}
	 * mátrixára, és a pozíciójára, ami az eltolása a pályához képest. Csak a nem
	 * nulla helyek ütközhetnek. Négy különböző helyen ütközhet az {@link Shape}: a 
	 * {@code Board} aljával, jobb és bal oldalával, és egy a pályán már lehelyezett 
	 * alakzattal. 
	 * 
	 * @param shape a {@link Shape} alakzat
	 * @return {@code true}, ha van ütközés, egyébként {@code false}
	 */
	public boolean hasCollision(Shape shape)
	{
		
        int[][] shapeMatrix = shape.getMatrix();	//elkérem a mátrixot a művelethez
        Position shapePosition = shape.getPosition();	//az alakzat az eltolása a boardhoz képest	
        for(int i = 0; i < MatrixHelper.getRowNum(shapeMatrix); i++)
		{
			  for(int j = 0; j < MatrixHelper.getColNum(shapeMatrix); j++)
			  {
				  //csak a nem nulla helyek ütközhetnek
				  //a this.matrix a pályámnak a mátrixa
				  //annyi oszlopa a mátrixnak, ahány eleme az első sorának
                if (shapeMatrix[i][j] != 0 && (
                		//lefelé kimentem a pályáról
                    i + shapePosition.getY() >= MatrixHelper.getRowNum(this.matrix) ||
                    //balra kimentem a pályáról
                    j + shapePosition.getX() < 0 ||
                    //jobbra kimentem a pályáról az alakzattal
                    j + shapePosition.getX() >= MatrixHelper.getColNum(this.matrix) ||
                    //az alakzattal a már a pályán lévő alakzattal ütköztem
          		    //megtolom a helyére az alakzatot, és ott nézem, hogy ott ütközik-e
                    this.matrix[i + shapePosition.getY()][j + shapePosition.getX()] != 0))
                    return true;	//van ütközés
			  }
        }
        return false;
	}
	
	/**
	 * Az {@link Shape} mentése a pályára. Ehhez szükség van az {@link Shape} mátrixára és a
	 * pozíciójára. Az {@link Shape} mátrixának a nullától különböző elemei rámásolódnak
	 * a {@code Board} mátrixának a megfelelő pozícióira. Minden egyes {@link Shape} lehelyezése
	 * után módosul a {@code Board} mátrixa.
	 * 
	 * @param shape a {@code Shape} alakzat
	 * @see Shape
	 */
	public void saveShape(Shape shape)
	{
		int[][] shapeMatrix = shape.getMatrix();	//elkérem az alakzat mátrixát
		Position shapePosition = shape.getPosition();	//elkérem az alakzat pozícióját
		
		//bejárom az alakzat mátrixát
		for(int i = 0; i < MatrixHelper.getRowNum(shapeMatrix); i++)
		{
			  for(int j = 0; j < MatrixHelper.getColNum(shapeMatrix); j++)
			  {
				  if (shapeMatrix[i][j] != 0)
				  {
					  //az alakzat mátrixának elemeit rámásolom a pálya mátrixának a megfelelő pozícióira
					  this.matrix[i + shapePosition.getY()][j + shapePosition.getX()] = shapeMatrix[i][j];
				  }
			  }
			  
		}
	}

	/**
	 * A teli sor eltüntetésének megvalósítása a pályáról. Teli a sor, ha nincsbenne 0 elem. 
	 * A teli sor fölötti sorok rácsúsznak a teli sorra, és felül csupa 0 sor jön be.
	 * Minden egyes eltüntetett sor 10 pontot ér a játékban.
	 * 
	 * @return a pontok száma
	 */
	public int boardSweep()
	{
		int points = 0;	//alaphelyzetben 0 pontom van
        for (int row = 0; row < MatrixHelper.getRowNum(this.matrix); row++)
        {
            boolean canSweep = true;	//eltüntethető-e a sor
            for (int column = 0; column < MatrixHelper.getColNum(this.matrix); column++)
            {
                if (this.matrix[row][column] == 0)
                {
                    canSweep = false;	//a sor nem törölhető
                    break;	//nincs értelme tovább vizsgálni ezt a sort
                }
            }
            if (canSweep)	//ha viszont teli a sor
            {
                MatrixHelper.sweepRow(this.matrix, row);	//mátrix sorainak cseréje
                points += 10;	//tíz pontot ér egy eltüntetett sor
            }
        }
        return points;
	}
}
