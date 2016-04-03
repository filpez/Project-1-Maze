package maze.logic;

/**
 * The wall entity represents a static object of the maze that cannot be traversed.
 * 
 */

public class Wall extends Entity {

	public Wall(){
		super();
		this.living = false;
		this.traversable = false;
	}
}