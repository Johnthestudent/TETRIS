package Tetris;

/**
 * A {@code Matrix} osztálya, mely {@code int} mátrixot reprezentál.
 */
public class Matrix 
{
	/**
	 * A {@code int} mátrix megfeleltetése, mint kétdimenziós tömb.
	 */
	protected int[][] matrix;	//az alap kétdimenziós tömb
	
	/**
	 * Visszaadja a mátrixot.
	 * 
	 * @return a mátrix
	 */
	public int[][] getMatrix()
	{
		return this.matrix;	//hozzáférjek a mátrixhoz
	}
	
	/**
	 * Konstruktor a {@code Matrix} objektum létrehozásához.
	 */
	public Matrix() {}
}
