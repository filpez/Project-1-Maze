//package maze.test;
//
//import static org.junit.Assert.*;
//
//import java.awt.Point;
//
//import org.junit.Test;
//
//import maze.logic.Entity;
//import maze.logic.Game;
//
//public class StaticGameTest {
//	char [][] m1 = {
//			{'X', 'X', 'X', 'X', 'X'},
//			{'X', ' ', ' ', 'H', 'S'},
//			{'X', ' ', 'X', ' ', 'X'},
//			{'X', 'E', ' ', 'D', 'X'},
//			{'X', 'X', 'X', 'X', 'X'}};
//	
//
//	@Test
//	public void testMoveHeroToFreeCell() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		game.takeTurn(Game.Movement.LEFT);
//		assertEquals(new Point(2,1), game.getHero().getPosition());	
//	}
//
//	@Test
//	public void testMoveHeroToWallCell() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		game.takeTurn(Game.Movement.UP);
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//	}
//	
//	@Test
//	public void testMoveHeroToCellWithSword() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		assertEquals(new Point(1,3), game.getEntities()[0].getPosition());
//		assertEquals(Entity.Type.ITEM, game.getEntities()[0].getType());
//		assertEquals(Entity.Item.SWORD, game.getEntities()[0].getItem());
//		assertEquals(Entity.Status.NORMAL, game.getEntities()[0].getStatus());	
//		assertEquals('H', game.getHero().getSymbol());
//		
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.DOWN);
//		
//		//Hero Position and sword possession test
//		assertEquals(new Point(1,3), game.getHero().getPosition());
//		assertEquals('A', game.getHero().getSymbol());
//		assertEquals('A', game.getLayout()[3][1]);
//		//Sword type check and dead status after been picked
//		assertEquals(Entity.Status.DEAD, game.getEntities()[0].getStatus());	
//	}
//	
//	@Test
//	public void testMoveHeroToCellNearDragon() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		assertEquals(Entity.Status.NORMAL, game.getHero().getStatus());	
//		assertEquals(new Point(3,3), game.getEntities()[1].getPosition());
//		assertEquals(Entity.Type.ENEMY, game.getEntities()[1].getType());
//		assertEquals(Entity.Status.NORMAL, game.getEntities()[1].getStatus());	
//		
//
//		game.takeTurn(Game.Movement.DOWN);
//		
//		//Hero Position and dead test
//		assertEquals(new Point(3,2), game.getHero().getPosition());
//		assertEquals(Entity.Status.DEAD, game.getHero().getStatus());	
//		assertEquals(Game.State.LOST, game.getState());	
//	}
//	
//	@Test
//	public void testKillDragon() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//				
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.RIGHT);
//		
//		//Hero Position
//		assertEquals(new Point(2,3), game.getHero().getPosition());
//		//Dragon killed
//		assertEquals(Entity.Status.DEAD, game.getEntities()[1].getStatus());
//		assertEquals(Entity.Status.NORMAL, game.getHero().getStatus());	
//	}
//	
//	@Test
//	public void testMoveHeroToExitAfterKillingDragon() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//				
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.RIGHT);
//		game.takeTurn(Game.Movement.RIGHT);
//		game.takeTurn(Game.Movement.UP);
//		game.takeTurn(Game.Movement.UP);
//		game.takeTurn(Game.Movement.RIGHT);
//		
//		//Hero Position
//		assertEquals(new Point(4,1), game.getHero().getPosition());
//		assertEquals(Game.State.WON, game.getState());	
//	}
//	
//	@Test
//	public void testMoveHeroToExit() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//				
//		game.takeTurn(Game.Movement.RIGHT);
//		
//		//Hero Position
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		assertEquals(Game.State.RUNNING, game.getState());	
//	}
//	
//	@Test
//	public void testMoveHeroToExitWithSwordButDragonAlive() {
//		Game game = new Game(m1, Game.Mode.STATIC);
//				
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.LEFT);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.DOWN);
//		game.takeTurn(Game.Movement.UP);
//		game.takeTurn(Game.Movement.UP);
//		game.takeTurn(Game.Movement.RIGHT);
//		game.takeTurn(Game.Movement.RIGHT);
//		game.takeTurn(Game.Movement.RIGHT);
//		
//		//Hero Position
//		assertEquals(new Point(3,1), game.getHero().getPosition());
//		assertEquals(Game.State.RUNNING, game.getState());	
//	}
//}
