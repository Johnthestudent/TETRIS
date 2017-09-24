package Tetris.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Tetris.MatrixHelper;
import Tetris.Position;
import Tetris.Shapes.Board;
import Tetris.Shapes.CrossShape;
import Tetris.Shapes.TShape;

public class BoardTest 
{
	
	private static Board peldany;
	
	//egyetlen egyszer fut le
	static
	{
		peldany = new Board(12, 20);
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
	public void WidthTest()
	{
		int elvart = 12;
		assertEquals(elvart, peldany.getWidth());
	}
	
	@Test
	public void CreateBoardTest()
	{
		peldany.initBoard();
		for(int i = 0; i < MatrixHelper.getRowNum(peldany.getMatrix()); i++)
		{
			for(int j = 0; j < MatrixHelper.getColNum(peldany.getMatrix()); j++)
			{
				assertEquals(0, peldany.getMatrix()[i][j]);
			}
		}
		peldany.initBoard();
	}
	
	@Test
	public void ShapeSavingTest()
	{
		TShape t = new TShape(new Position(0, 0));	//az elmentendő mátrix
		peldany.saveShape(t);
		assertEquals(7, peldany.getMatrix()[0][0]);
		assertEquals(7, peldany.getMatrix()[0][1]);
		assertEquals(7, peldany.getMatrix()[0][2]);
		assertEquals(7, peldany.getMatrix()[1][1]);	//adott elemek megfelelnek-e
		peldany.initBoard(); //visszaállítom a pályát
	}
	
	@Test
	public void BoardSweepingTest()
	{
		peldany.initBoard();
		for(int j = 0; j < MatrixHelper.getColNum(peldany.getMatrix()); j++)
		{
			peldany.getMatrix()[0][j] = 1;	//csupa egyes lesz a sorban
			peldany.getMatrix()[1][j] = 1;
			peldany.getMatrix()[2][j] = 1;
		}
		peldany.boardSweep();
		for(int i = 0; i < MatrixHelper.getRowNum(peldany.getMatrix()); i++)
		{
			for(int j = 0; j < MatrixHelper.getColNum(peldany.getMatrix()); j++)
			{
				assertEquals(0, peldany.getMatrix()[i][j]);
			}
		}
		peldany.initBoard(); //kezdeti állapot visszállítása
	}
}
