package Tetris;

/**
 * A {@code Position} osztálya, mely x és y koordináták műveleteit határozza meg az alakzatok
 * mozgatásához.
 */
public class Position 
{
	/**
	 * Az x szerinti eltolás mértéke.
	 */
	private int x;
	
	/**
	 * Az y szerinti eltolás mértéke.
	 */
	private int y;
	
	/**
	 * Konstruktor az x és y szerinti eltolások beállítására.
	 * 
	 * @param x az x szerinti eltolás
	 * @param y az y szerinti eltolás
	 */
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Visszaadja az x szerinti eltolás mértékét.
	 * 
	 * @return x szerinti eltolás mértéke
	 */
	public int getX()
	{
		return this.x;
	}
	
	/**
	 * Visszaadja az y szerinti eltolás mértékét.
	 * 
	 * @return y szerinti eltolás mértéke
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Beállítja az x szerinti eltolás mértékét.
	 * 
	 * @param x az x szerinti eltolás
	 */
	public void setX(int x) 
	{
		this.x = x;
	}

	/**
	 * Beállítja az y szerinti eltolás mértékét.
	 * 
	 * @param y az y szerinti eltolás
	 */
	public void setY(int y) 
	{
		this.y = y;
	}

	/**
	 * Növeli az x szerinti eltolás mértékét.
	 */
	public void incrementX()
	{
		x += 1;
	}

	/**
	 * Növeli az y szerinti eltolás mértékét.
	 */
	public void incrementY()
	{
		y += 1;
	}

	/**
	 * Csökkenti az x szerinti eltolás mértékét.
	 */
	public void decrementX()
	{
		x -= 1;
	}

	/**
	 * Csökkenti az y szerinti eltolás mértékét.
	 */
	public void decrementY()
	{
		y -= 1;
	}

	/**
	 * Objektumok egyenlőségének vizsgálata. Két {@code Position} objektum akkor egyenlő, ha x és y
	 * koordinátáik értékei egyenlőek.
	 * 
	 *  @return {@code true}, ha a két objektum x és y koordinátái egyenlőek
	 */
	@Override public boolean equals(Object obj) 
	{
		//ha az objektum null vagy nem Position, akkor false
	    if((obj==null)||!(obj instanceof Position)) return false;
	    Position b =(Position)obj;	//objektumom biztos, hogy Position
	   
	    return b.getX() == this.getX() && b.getY() == this.getY();
	}
}
