package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code LShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class LShape extends Shape
{
	/**
	 * Az {@code LShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public LShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{0, 3, 0}, 
			{0, 3, 0}, 
			{0, 3, 3}};	//tényleges mátrix
			this.easyheuristicValue = 4;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 1;	//heurisztika érték nehéz játék mód esetén
	}
}
