package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code TShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class TShape extends Shape
{
	/**
	 * A {@code TShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public TShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{7, 7, 7}, 
			{0, 7, 0}, 
			{0, 0, 0}};	//tényleges mátrix
	}
}
