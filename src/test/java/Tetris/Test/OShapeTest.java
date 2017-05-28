package Tetris.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Tetris.MatrixHelper;
import Tetris.Position;
import Tetris.Shapes.OShape;

public class OShapeTest 
{
	private OShape peldany;
	
	@Before
	public void setup()
	{
		peldany = new OShape(new Position(0, 0));
	}
	
	@After
	public void teardown()
	{
		peldany = null;
	}
	
	@Test
	public void RotateRightTest()
	{
		int[][] elvart = new int[][]{
			{4, 4}, 
			{4, 4}};	//ami mátrixot várok
		peldany.rotateRight();	//még a vizsgálat előtt elforgatom a mátrixot
		for(int i = 0; i < MatrixHelper.getRowNum(peldany.getMatrix()); i++)
		{
			//nem elforgatott és elforgatott mátrix ugyanakkora
			assertArrayEquals(i+ " sora a mátrixnak", elvart[i], peldany.getMatrix()[i]);
		}
		peldany.rotateLeft();
	}
	
	@Test
	public void RotateLeftTest()
	{
		int[][] elvart = new int[][]{
			{4, 4}, 
			{4, 4}};	//ami mátrixot várok
		peldany.rotateLeft();	//még a vizsgálat előtt elforgatom a mátrixot
		for(int i = 0; i < MatrixHelper.getRowNum(peldany.getMatrix()); i++)
		{
			//nem elforgatott és elforgatott mátrix ugyanakkora
			assertArrayEquals(i+ " sora a mátrixnak", elvart[i], peldany.getMatrix()[i]);
		}
		peldany.rotateRight();
	}
	
	@Test
	public void MoveLeftTest()
	{
		Position elvart = new Position(0, 0);
		elvart.decrementX();
		peldany.moveLeft();
			assertEquals(elvart, peldany.getPosition()); //ez meghívja a Position equals metódusát
		peldany.moveRight();  //példány állapotának visszaállítása
	}
	
	@Test
	public void MoveRightTest()
	{
		Position elvart = new Position(0, 0);
		elvart.incrementX();
		peldany.moveRight();
			assertEquals(elvart, peldany.getPosition()); //ez meghívja a Position equals metódusát
		peldany.moveLeft();  //példány állapotának visszaállítása
	}
	
	@Test
	public void MoveUpTest()
	{
		Position elvart = new Position(0, 0);
		elvart.decrementY();
		peldany.moveUp();
			assertEquals(elvart, peldany.getPosition()); //ez meghívja a Position equals metódusát
		peldany.moveDown();  //példány állapotának visszaállítása
	}
	
	@Test
	public void MoveDownTest()
	{
		Position elvart = new Position(0, 0);
		elvart.incrementY();
		peldany.moveDown();
			assertEquals(elvart, peldany.getPosition()); //ez meghívja a Position equals metódusát
		peldany.moveUp();  //példány állapotának visszaállítása
	}
	
	@Test
	public void WidthTest()
	{
		int elvart = 2;
		assertEquals(elvart, peldany.getWidth());
	}
	
	@Test
	public void HeightTest()
	{
		int elvart = 2;
		assertEquals(elvart, peldany.getHeight());
	}
}