package maze.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import maze.logic.Game.Mode;

public class MazeLogic {
	public enum Movement {UP, DOWN, LEFT, RIGHT, NULL}

	private static final int SLEEP_CHANGE = 20;
	private static final int AWAKE_CHANGE = 20;
	private static final int NO_MOVEMENT_CHANGE = 20;

	private static boolean tryBehaviour(int change){
		Random random = new Random();
		if (random.nextInt(100) < change)
			return true;
		return false;
	}

	private static boolean isStuck(Maze maze, Point position){
		int x = position.x;
		int y = position.y;
		if (	isFree(maze, new Point(x+1,y)) ||
				isFree(maze, new Point(x-1,y)) ||
				isFree(maze, new Point(x,y+1)) ||
				isFree(maze, new Point(x,y-1)))
			return false;
		return true;
	}

	private static Movement randomDirection(){
		Random random = new Random();
		return Movement.values()[random.nextInt(Movement.values().length - 1)];
	}

	public static boolean move(Maze maze, Point position, int index, Mode mode) {
		Entity ent = maze.getEntity(position, index);
		Movement movDirection = Movement.NULL;
		if (!isStuck(maze, position)){
			Point dest = new Point(position);
			switch (mode){
			case RANDOM:
				if (tryBehaviour(NO_MOVEMENT_CHANGE))
					break;
				while (!isFree(maze, dest)){
					movDirection =  randomDirection();
					dest = newPosition(position, movDirection);
				}
				break;
			case SLEEP_RANDOM:
				switch (ent.getStatus()){
				case SLEEPING:
					if (tryBehaviour(AWAKE_CHANGE))
						ent.setStatus(Entity.Status.NORMAL);
					break;
				case NORMAL:
					if (tryBehaviour(SLEEP_CHANGE))
						ent.setStatus(Entity.Status.SLEEPING);
					else if (!tryBehaviour(NO_MOVEMENT_CHANGE))
						while (!isFree(maze, dest)){
							movDirection =  randomDirection();
							dest = newPosition(position, movDirection);
						}
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
		}
		return move(maze, position, index, movDirection);
	}

	private static Point newPosition(Point init, Movement movDirection){
		Point dest = new Point(init);
		switch(movDirection){
		case UP:
			dest.y -= 1;
			break;
		case DOWN:
			dest.y += 1;
			break;
		case LEFT:
			dest.x -= 1;
			break;
		case RIGHT:
			dest.x += 1;
			break;
		default:	
			break;
		}
		return dest;
	}

	public static boolean move(Maze maze, Point init, int index, Movement movDirection) {
		Point dest = newPosition(init, movDirection);

		if (!isFree(maze, dest))
			return false;

		maze.moveEntity(init, index, dest);
		return true;	
	}

	private static boolean isFree(Maze maze, Point dest) {
		ArrayList<Entity> currentCell = maze.getCell(dest);
		for(Entity ent: currentCell)
			if (!ent.isTraversable())
				return false;
		return true;
	}

	private static void checkForDragon(Maze maze, Point position) {
		if (maze.validPoint(position)){	
			ArrayList<Entity> currentCell = maze.getCell(position);
			for (Entity ent: currentCell)
				if (ent instanceof Dragon && ent.getStatus() != Entity.Status.DEAD){
					if (maze.getHero().isArmed())
						ent.kill();
					else if (ent.getStatus() != Entity.Status.SLEEPING)
						maze.getHero().kill();
				}
		}
	}

	private static void checkForSword(Maze maze, Point position) {
		if (maze.validPoint(position)){	
			ArrayList<Entity> currentCell = maze.getCell(position);
			for (Entity ent: currentCell)
				if (ent instanceof Sword && ent.getStatus() != Entity.Status.DEAD){
					maze.getHero().setArmed(true);
					ent.kill();
				}
		}
	}

	private static void checkForExit(Maze maze) {
		int dragonCounter = 0;
		for (Point position: maze.getAllPositions()){
			for (Entity ent: maze.getCell(position)){
				if (ent instanceof Dragon && ent.getStatus() != Entity.Status.DEAD)
					dragonCounter++;
			}
		}

		if (dragonCounter == 0){
			for (Point position: maze.getAllPositions()){
				for (Entity ent: maze.getCell(position)){
					if (ent instanceof Exit && ent.getStatus() != Entity.Status.DEAD)
						ent.kill();
				}
			}
		}



	}

	public static void colisions_handler(Maze maze) {
		Point upperCellKey = newPosition(maze.getHeroKey(), Movement.UP);
		Point lowerCellKey = newPosition(maze.getHeroKey(), Movement.DOWN);
		Point rightCellKey = newPosition(maze.getHeroKey(), Movement.RIGHT);
		Point leftCellKey = newPosition(maze.getHeroKey(), Movement.LEFT);

		checkForSword(maze, maze.getHeroKey());
		checkForDragon(maze, upperCellKey);
		checkForDragon(maze, lowerCellKey);
		checkForDragon(maze, rightCellKey);
		checkForDragon(maze, leftCellKey);
		checkForExit(maze);
	}





}
