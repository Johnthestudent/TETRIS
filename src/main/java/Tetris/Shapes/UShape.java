package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code UShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class UShape extends Shape
{
	/**
	 * A {@code UShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public UShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{8, 0, 8}, 
			{8, 8, 8}, 
			{0, 0, 0}};	//tényleges mátrix
			this.easyheuristicValue = 1;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 4;	//heurisztika érték nehéz játék mód esetén
	}
}

