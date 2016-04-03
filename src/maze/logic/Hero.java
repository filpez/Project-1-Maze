package maze.logic;

/**
 * The hero entity represents a dynamic object of the maze that cannot be traversed.
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
