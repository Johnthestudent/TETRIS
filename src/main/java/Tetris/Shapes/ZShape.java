package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code ZShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class ZShape extends Shape
{
	/**
	 * A {@code ZShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public ZShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{6, 6, 0}, 
			{0, 6, 6}, 
			{0, 0, 0}};	//tényleges mátrix
			this.easyheuristicValue = 3;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 2;	//heurisztika érték nehéz játék mód esetén
	}
}