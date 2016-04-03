package maze.logic;

import java.awt.Point;
import java.util.ArrayList;

public class Game {
	public enum State {RUNNING, WON, LOST}
	public enum Mode {STATIC, SLEEP_RANDOM, RANDOM}

	private State state = State.RUNNING;
	private Maze maze;
	private Mode mode;

	public Game(Maze maze, Mode mode){
		this.maze = maze;
		this.mode = mode;
	}

	public Maze getMaze() {
		return maze;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void takeTurn(MazeLogic.Movement movDirection){
		//Resets entity movements
		for (Point position: maze.getAllPositions()){
			for (Entity ent: maze.getCell(position)){
				ent.setMoved(false);
			}
		}

		//Moves Hero
		MazeLogic.move(maze, maze.getHeroKey(), maze.getHeroIndex(), movDirection);

		//Moves Remaining movable entities
		for (Point position: maze.getAllPositions()){
			if(!maze.getHeroKey().equals(position)){	
				ArrayList<Entity> currentCell = maze.getCell(position);
				int index = 0;
				while(index < currentCell.size()){
					Entity ent = currentCell.get(index);
					if (ent.isLiving() && !ent.hasMoved())
						MazeLogic.move(maze, position, index, mode);
					index++;
				}	
			}
		}

		//Checks for collisions and handles then
		MazeLogic.colisions_handler(maze);

		//Check Win/Lose Conditions
		if (winningConditions()){
			state = State.WON;
			return;
		}
		if (losingConditions()){
			state = State.LOST;
			return;
		}
	}

	private boolean losingConditions() {
		if (maze.getHero().getStatus() == Entity.Status.DEAD)
			return true;
		return false;
	}

	private boolean winningConditions() {
		ArrayList<Entity> currentHeroCell = maze.getCell(maze.getHeroKey());
		for (Entity ent: currentHeroCell)
			if (ent instanceof Exit)
				return true;
		return false;
	}
}
