package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code SShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class SShape extends Shape
{
	/**
	 * Az {@code SShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public SShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{0, 5, 5}, 
			{5, 5, 0}, 
			{0, 0, 0}};	//tényleges mátrix
			this.easyheuristicValue = 3;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 2;	//heurisztika érték nehéz játék mód esetén
	}
}