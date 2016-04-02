//package maze.test;
//
//import static org.junit.Assert.*;
//
//import java.awt.Point;
//
//import org.junit.Test;
//
//import maze.logic.Game;
//import maze.logic.Entity;
//
//public class NonStaticGameTest {
//	char [][] m1 = {
//			{'X', 'X', 'X', 'X', 'X', 'X', 'X'},
//			{'X', 'H', ' ', ' ', ' ', ' ', 'S'},
//			{'X', ' ', 'X', 'X', 'X', 'X', 'X'},
//			{'X', ' ', 'X', 'X', ' ', 'X', 'X'},
//			{'X', ' ', 'X', ' ', 'D', ' ', 'X'},
//			{'X', 'E', 'X', 'X', ' ', 'X', 'X'},
//			{'X', 'X', 'X', 'X', 'X', 'X', 'X'}};
//
//
//	@Test
//	public void testRandomDragonMovement() {
//		Game game = new Game(m1, Game.Mode.RANDOM);
//		while (!game.randomMove(game.getEntities()[0])){
//			assertEquals(new Point(4,4), game.getEntities()[0].getPosition());
//		}
//		assertNotEquals(new Point(4,4), game.getEntities()[0].getPosition());
//	}
//
//	@Test
//	public void testSleepRandomDragonMovement() {
//		Game game = new Game(m1, Game.Mode.SLEEP_RANDOM);
//		while (game.getEntities()[0].getStatus() == Entity.Status.NORMAL){
//			Point position = new Point(game.getEntities()[0].getPosition());
//			if(game.randomMove(game.getEntities()[0]))
//				assertNotEquals(position, game.getEntities()[0].getPosition());
//			else
//				assertEquals(position, game.getEntities()[0].getPosition());
//		}
//		assertEquals(Entity.Status.SLEEPING, game.getEntities()[0].getStatus());
//		Point position = new Point(game.getEntities()[0].getPosition());
//		while (game.getEntities()[0].getStatus() == Entity.Status.SLEEPING){
//			game.update();
//			assertEquals('d', game.getEntities()[0].getSymbol());
//			assertEquals('d', game.getLayout()[position.y][position.x]);
//			assertEquals(position, game.getEntities()[0].getPosition());
//			game.randomMove(game.getEntities()[0]);
//		}
//		assertEquals(Entity.Status.NORMAL, game.getEntities()[0].getStatus());
//	}
//
//
//}
