package maze.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import maze.logic.Entity;
import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.MazeLogic;
import maze.logic.CharBuilder;
import maze.logic.Sword;
import maze.logic.Dragon;

public class StaticGameTest {
	char [][] m1 = {
			{'X', 'X', 'X', 'X', 'X'},
			{'X', ' ', ' ', 'H', 'S'},
			{'X', ' ', 'X', ' ', 'X'},
			{'X', 'E', ' ', 'D', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};
	

	@Test
	public void testMoveHeroToFreeCell() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);	
		assertEquals(new Point(3,1), m.getHeroKey());
		game.takeTurn(MazeLogic.Movement.LEFT);
		assertEquals(new Point(2,1), m.getHeroKey());	
	}

	@Test
	public void testMoveHeroToWallCell() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
		assertEquals(new Point(3,1), m.getHeroKey());
		game.takeTurn(MazeLogic.Movement.UP);
		assertEquals(new Point(3,1), m.getHeroKey());
	}
	
	@Test
	public void testMoveHeroToCellWithSword() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
			
		assertEquals(new Point(3,1), m.getHeroKey());
		assertEquals(true, m.getCell(new Point(1,3)).get(0) instanceof Sword);
		assertEquals(Entity.Status.NORMAL, m.getCell(new Point(1,3)).get(0).getStatus());	
		assertEquals(false, m.getHero().isArmed());
		
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.DOWN);
		
		//Hero Position and sword possession test
		assertEquals(new Point(1,3), m.getHeroKey());
		assertEquals(true, m.getHero().isArmed());
		
		//Sword type check and dead status after been picked
		assertEquals(Entity.Status.DEAD, m.getCell(new Point(1,3)).get(0).getStatus());	
	}
	
	@Test
	public void testMoveHeroToCellNearDragon() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
		assertEquals(new Point(3,1), m.getHeroKey());
		assertEquals(Entity.Status.NORMAL, m.getHero().getStatus());
		assertEquals(true, m.getCell(new Point(3,3)).get(0) instanceof Dragon);
		assertEquals(Entity.Status.NORMAL, m.getCell(new Point(3,3)).get(0).getStatus());	
		
		game.takeTurn(MazeLogic.Movement.DOWN);
		
		//Hero Position and dead test
		assertEquals(new Point(3,2), m.getHeroKey());
		assertEquals(Entity.Status.DEAD, m.getHero().getStatus());	
		assertEquals(Game.State.LOST, game.getState());	
	}
	
	@Test
	public void testKillDragon() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
				
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		
		//Hero Position
		assertEquals(new Point(2,3), m.getHeroKey());
		//Dragon killed
		assertEquals(Entity.Status.DEAD, m.getCell(new Point(3,3)).get(0).getStatus());
		assertEquals(Entity.Status.NORMAL, m.getHero().getStatus());	
	}
	
	@Test
	public void testMoveHeroToExitAfterKillingDragon() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
				
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		game.takeTurn(MazeLogic.Movement.UP);
		game.takeTurn(MazeLogic.Movement.UP);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		
		//Hero Position
		assertEquals(new Point(4,1), m.getHeroKey());
		assertEquals(Game.State.WON, game.getState());	
	}
	
	@Test
	public void testMoveHeroToExit() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
				
		game.takeTurn(MazeLogic.Movement.RIGHT);
		
		//Hero Position
		assertEquals(new Point(3,1), m.getHeroKey());
		assertEquals(Game.State.RUNNING, game.getState());	
	}
	
	@Test
	public void testMoveHeroToExitWithSwordButDragonAlive() {
		Maze m = CharBuilder.getMaze(m1);
		Game game = new Game(m, Game.Mode.STATIC);
				
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.LEFT);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.DOWN);
		game.takeTurn(MazeLogic.Movement.UP);
		game.takeTurn(MazeLogic.Movement.UP);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		game.takeTurn(MazeLogic.Movement.RIGHT);
		
		//Hero Position
		assertEquals(new Point(3,1), m.getHeroKey());
		assertEquals(Game.State.RUNNING, game.getState());	
	}
}
