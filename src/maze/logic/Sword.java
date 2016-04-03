package maze.logic;

/**
 * The Sword entity represents a static object of the Maze that can be traversed.
 * When the Hero entity goes through the Sword, the Sword entity is "killed".
 * 
 */

public class Sword extends Entity{
	
	public Sword(){
		super();
		this.living = false;
		this.traversable = true;
	}
}