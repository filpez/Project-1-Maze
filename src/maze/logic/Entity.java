package maze.logic;

/**
 * An Entity represents any of the objects that can exist on the Maze throughout the Game.
 * 
 * @author Filipe
 *
 */
public abstract class Entity {
	public enum Status {NORMAL, SLEEPING, DEAD}

	protected Status status;
	protected boolean living;
	protected boolean traversable;
	protected boolean moved;
	
	public Entity(){
		this.status = Status.NORMAL;
		this.moved = false;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isLiving() {
		return living;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}

	public boolean isTraversable() {
		return traversable;
	}

	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}	
	
	public boolean hasMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	/**
	 * Sets living to false, status to Status.DEAD, and traversable to true.
	 * Kills an entity
	 */
	public void kill() {
		this.living = false;
		this.status = Status.DEAD;
		this.traversable = true;
	}
}

