package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code CrossShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class CrossShape extends Shape
{
	/**
	 * A {@code CrossShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public CrossShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{0, 9, 0}, 
			{9, 9, 9}, 
			{0, 9, 0}};	//tényleges mátrix
	}
}
