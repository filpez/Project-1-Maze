package maze.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

public class CharBuilder {
	
	/**
	 * Converts a 2D array of chars in a Hashtable.
	 * @param layout - 2D array containing the arrangement of the Maze
	 * @return Maze equivalent to layout
	 */
	public static Maze getMaze(char layout[][]) {
		int dimension = layout.length;
		Point point = new Point(0,0);
		Hashtable<Point, ArrayList<Entity>> maze = new Hashtable<Point, ArrayList<Entity>>();
		for (char row[]: layout){
			for (char tile: row){
				ArrayList<Entity> list = new ArrayList<Entity>();
				switch(tile){
				case 'H':
					list.add(new Hero());
					break;
				case 'D':
					list.add(new Dragon());
					break;
				case 'E':
					list.add(new Sword());
					break;
				case 'X':
					list.add(new Wall());
					break;
				case 'S':
					list.add(new Exit());
					break;
				default:
					break;
				}
				maze.put(new Point(point), list);
				point.x++;
			}
			point.y++;
			point.x = 0;
		}
		return new Maze(maze, dimension);
	}
	
	public static Maze getMaze(int dimension, int numberOfDragons) {
		char layout[][] = new MazeBuilder().buildMaze(dimension, numberOfDragons, 1);
		return getMaze(layout);
	}
	
}
