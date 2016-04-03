package maze.logic;

/**
 * The Wall entity represents a static object of the Maze that cannot be traversed.
 * 
 */

public class Wall extends Entity {

	public Wall(){
		super();
		this.living = false;
		this.traversable = false;
	}
}