package maze.logic;

/**
 * The Hero entity represents a dynamic object of the Maze that cannot be traversed.
 * The player controls this entity and the loss or victory of the Game depends on the Hero.
 * 
 */

public class Hero extends Entity {
	
	private boolean armed;
	
	public Hero(){
		super();
		this.living = true;
		this.armed = false;
		this.traversable = false;
	}

	public boolean isArmed() {
		return armed;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	
	
}
