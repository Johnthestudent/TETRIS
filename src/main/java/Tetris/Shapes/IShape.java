package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code IShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class IShape extends Shape
{
	/**
	 * Az {@code IShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat {@code Position} pozíciója
	 * @see Position
	 */
	public IShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{0, 1, 0, 0}, 
			{0, 1, 0, 0}, 
			{0, 1, 0, 0},
			{0, 1, 0, 0}};	//tényleges mátrix
			this.easyheuristicValue = 4;	//heurisztika érték könnyű játék mód esetén
			this.hardheuristicValue = 1;	//heurisztika érték nehéz játék mód esetén
	}
}
