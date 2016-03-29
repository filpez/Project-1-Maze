package maze.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

public class Maze {
	private Hashtable<Point, ArrayList<Entity>> maze;
	private int dimension;
	
	
	public Maze(Hashtable<Point, ArrayList<Entity>> maze, int dimension) {
		super();
		this.maze = maze;
		this.dimension = dimension;
	}
	
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	
	
}
