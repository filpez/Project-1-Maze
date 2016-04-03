package maze.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.MazeLogic;
import maze.logic.CharBuilder;
import maze.logic.Entity;
import maze.logic.Dragon;

public class NonStaticGameTest {
	char [][] m1 = {
			{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', ' ', ' ', ' ', 'S'},
			{'X', ' ', 'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', 'X', 'X', ' ', 'X', 'X'},
			{'X', ' ', 'X', ' ', 'D', ' ', 'X'},
			{'X', 'E', 'X', 'X', ' ', 'X', 'X'},
			{'X', 'X', 'X', 'X', 'X', 'X', 'X'}};


	@Test
	public void testRandomDragonMovement() {
		Maze m = CharBuilder.getMaze(m1);
		
		Point position = new Point(4,4);
		int index = 0;
		
		while (!MazeLogic.move(m, position, index, Game.Mode.RANDOM)){
			assertEquals(true, m.getCell(position).get(index) instanceof Dragon);
		}

		assertEquals(0, m.getCell(position).size());
	}

	@Test
	public void testSleepRandomDragonMovement() {
		Maze m = CharBuilder.getMaze(m1);
		//Game game = new Game(m, Game.Mode.SLEEP_RANDOM);

		Point position = new Point(4,4);
		int index = 0;

		while (m.getCell(position).get(0).getStatus() == Entity.Status.NORMAL){
			
			if(MazeLogic.move(m, position, index, Game.Mode.SLEEP_RANDOM))
				assertEquals(0, m.getCell(position).size());
			else
				assertEquals(true, m.getCell(position).get(index) instanceof Dragon);	
			
			for (Point p : m.getAllPositions()){
				ArrayList<Entity> CurrentCell = m.getCell(p);

				for(int i = 0; i < CurrentCell.size(); i++)
					if (CurrentCell.get(i) instanceof Dragon){
						index = i;
						position = p;
						break;
					}
			}
		}
			
		assertEquals(Entity.Status.SLEEPING, m.getCell(position).get(0).getStatus());
		
		while (m.getCell(position).get(0).getStatus() == Entity.Status.SLEEPING){
			if(MazeLogic.move(m, position, index, Game.Mode.SLEEP_RANDOM))
				assertEquals(0, m.getCell(position).size());
			else
				assertEquals(true, m.getCell(position).get(index) instanceof Dragon);
			
			for (Point p : m.getAllPositions()){
				ArrayList<Entity> CurrentCell = m.getCell(p);

				for(int i = 0; i < CurrentCell.size(); i++)
					if (CurrentCell.get(i) instanceof Dragon){
						index = i;
						position = p;
						break;
					}
			}
		}	
		assertEquals(Entity.Status.NORMAL, m.getCell(position).get(0).getStatus());	 
	}
}
