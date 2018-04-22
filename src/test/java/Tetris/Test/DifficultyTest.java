package Tetris.Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Tetris.Difficulty;
import Tetris.Controller.*;
import javafx.scene.control.RadioButton;

public class DifficultyTest 
{
	private static Difficulty peldany, peldany2, peldany3;
	
	//egyetlen egyszer fut le
		static
		{
			peldany = Difficulty.HARD;
		}
		
		static
		{
			peldany2 = Difficulty.NORMAL;
		}
		
		static
		{
			peldany3 = Difficulty.EASY;
		}
		
		@Before
		public void setup()
		{
		}
		
		@After
		public void teardown()
		{
		}
		
		//tesztmetódus annak ellenőrzésére, hogy ha pl. hard nehézségi szintet
		//jelöl be a játékos, akkor tényleg hard módba vált-e a játék
		@Test
		public void DifficultyChosingTest()
		{
			Difficulty elvart = Difficulty.HARD;
			Difficulty elvart2 = Difficulty.NORMAL;
			Difficulty elvart3 = Difficulty.EASY;
			assertEquals(elvart, peldany);
			assertEquals(elvart2, peldany2);
			assertEquals(elvart3, peldany3);
		}
}
