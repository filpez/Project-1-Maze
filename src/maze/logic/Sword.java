package maze.logic;

/**
 * The sword entity represents a static object of the maze that can be traversed.
 * When the hero entity goes through the sword, the sword entity is "killed".
 * 
 */

public class Sword extends Entity{
	
	public Sword(){
		super();
		this.living = false;
		this.traversable = true;
	}
}