package Tetris.Shapes;

import Tetris.Position;

/**
 * Az {@code JShape} osztálya, melynek konstruktorában van megadva, hogy hogyan nézzen ki a mátrixa.
 * 
 * @see Shape
 */
public class JShape extends Shape
{
	/**
	 * A {@code JShape} mátrixbeli reprezentációja. Az alakzat az ősosztály
	 * konstruktorának a meghívásával kapja meg a pozíciót.
	 * 
	 * @param position az alakzat pozíciója
	 * @see Position
	 */
	public JShape(Position position)
	{
		super(position);	//ősosztály konstruktorát meghívom
		this.matrix = new int[][]{
			{0, 2, 0}, 
			{0, 2, 0}, 
			{2, 2, 0}};	//tényleges mátrix
	}
}