package Tetris;

import Tetris.Shapes.*;

//heurisztika számolás
public class HeuristicHelper 
{
	public static int getHeuristic(Board b, Shape s)
	{
		int result = 0;
		for(int i = 0; i < MatrixHelper.getRowNum(b.getMatrix()); i++)
		  {
			  	for(int j = 0; j < MatrixHelper.getColNum(b.getMatrix()); j++)
				{
			  		if(b.getMatrix()[i][j] == s.getColorCode())
			  		{
			  			result++;	//találtam egy olyan mezőt, ami adott értékű
			  		}	
				}
		  }
		return result;
	}
	

}
