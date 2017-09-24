package Tetris.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Tetris.MatrixHelper;
import Tetris.Position;
import Tetris.Shapes.IShape;
import Tetris.Shapes.UShape;

public class IShapeTest 
{
private static IShape peldany;
	
	//egyetlen egyszer fut le
	static
	{
		peldany = new IShape(new Position(0, 0));
	}
	
	@Before
	public void setup()
	{
	}
	
	@After
	public void teardown()
	{
	}
	
	@Test
	public void RotateRightTest()
	{
		int[][] elvart = new int[][]{
			{0, 0, 0, 0}, 
			{1, 1, 1, 1}, 
			{0, 0, 0, 0},
			{0, 0, 0, 0}};	//ami mátrixot várok
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
			{0, 0, 0, 0}, 
			{0, 0, 0, 0}, 
			{1, 1, 1, 1},
			{0, 0, 0, 0}};	//ami mátrixot várok
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
		int elvart = 4;
		assertEquals(elvart, peldany.getWidth());
	}
	
	@Test
	public void HeightTest()
	{
		int elvart = 4;
		assertEquals(elvart, peldany.getHeight());
	}
}
