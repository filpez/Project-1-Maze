package maze.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import maze.logic.Game.Mode;
import maze.logic.Game.State;
import maze.logic.MazeLogic.Movement;

public class MazeLogic {
	public enum Movement {UP, DOWN, LEFT, RIGHT, NULL}

	public static State takeTurn(Maze maze, Movement movDirection, Mode mode) {
		//Move Characters
		move(hero, movDirection);
		moveEntities();

		//Check Collisions
		colisions_handler();

		//Check Win/Lose Conditions
		if (maze.getTile(hero.getPosition()) == 'S')
			state = State.WON;
		if (hero.getStatus() == Entity.Status.DEAD)
			state = State.LOST;

		//Update and Display Maze
		update();
		return null;
	}
	
	
	
}
