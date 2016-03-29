package maze.logic;

public abstract class Entity {
	public enum Type {HERO, ENEMY, ITEM}
	public enum Status {NORMAL, SLEEPING, DEAD}

	protected Status status;
	protected boolean living;
	
	/**
	 * Constructor
	 * @param x	The horizontal coordinate.
	 * @param y	The vertical coordinate.
	 */
	public Entity(){
		this.status = Status.NORMAL;
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

	
	
}
