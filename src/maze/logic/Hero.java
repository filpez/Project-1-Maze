package maze.logic;
/*
 * Hero
 * 23/2/2016
 * 
 * by Filipe
 */

public class Hero extends Entity {
	
	private boolean armed;
	
	public Hero(){
		super();
		this.living = true;
		this.armed = false;
	}

	public boolean isArmed() {
		return armed;
	}

	public void setArmed(boolean armed) {
		this.armed = armed;
	}
	
	
}
