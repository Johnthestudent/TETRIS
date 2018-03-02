package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code OShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class OShape extends Shape
{
	/**
	 * Az {@code OShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public OShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{4, 4}, 
			{4, 4}}; //tényleges mátrix
			this.easyheuristicValue = 2;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 3;	//heurisztika érték nehéz játék mód esetén
	}
}