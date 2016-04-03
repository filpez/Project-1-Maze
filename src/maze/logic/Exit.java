package maze.logic;

/**
 * The Exit entity represents a static object of the Maze that can only be traversed when the Dragon(s) is/are killed.
 * When the Hero goes through the Exit, the Game is won.
 * 
 */

public class Exit extends Entity {

	public Exit(){
		super();
		this.living = false;
		this.traversable = false;
	}
}
