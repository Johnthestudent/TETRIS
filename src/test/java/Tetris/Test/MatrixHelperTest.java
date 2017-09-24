package Tetris.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Tetris.MatrixHelper;
import Tetris.Position;
import Tetris.Shapes.Board;
import Tetris.Shapes.UShape;

public class MatrixHelperTest 
{
	
	private static int[][] peldany;
	
	//egyetlen egyszer fut le
	static
	{
		peldany = new int[][]{{0, 0, 1}, 
							  {1, 1, 2},
							  {2, 2, 3}};
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
	public void RowNumTest()
	{
		int elvart = 3;
		assertEquals(elvart, MatrixHelper.getRowNum(peldany));
	}
	
	@Test
	public void ColNumTest()
	{
		int elvart = 3;
		assertEquals(elvart, MatrixHelper.getColNum(peldany));
	}
	
	@Test
	public void TransposeTest()
	{
		int[][] elvart = new int[][]{{0, 1, 2}, 
									 {0, 1, 2}, 
									 {1, 2, 3}};
		int[][] transzponalt = MatrixHelper.transpose(peldany);
		for(int i = 0; i < MatrixHelper.getRowNum(transzponalt); i++)
		{
			assertArrayEquals(i+ " sora a mátrixnak", elvart[i], transzponalt[i]);
		}
	}

	@Test
	public void ReverseRowTest()
	{
		int[][] elvart = new int[][]{{1, 0, 0}, 
									 {2, 1, 1}, 
									 {3, 2, 2}};
		int[][] reversed = MatrixHelper.reverseEachRow(peldany);
		for(int i = 0; i < MatrixHelper.getRowNum(reversed); i++)
		{
			assertArrayEquals(i+ " sora a mátrixnak", elvart[i], reversed[i]);
		}
	}
}
