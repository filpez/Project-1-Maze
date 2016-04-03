package maze.logic;

/**
 * The Dragon entity represents a static or dynamic object of the Maze that cannot be traversed (unless it's killed).
 * If the Hero arrives before the dragon unarmed, the Hero is "killed" and the Game is lost.
 * Otherwise, the Dragon is killed and the Exit of the Maze opens.
 * 
 */

public class Dragon extends Entity {
	
	public Dragon(){
		super();
		this.living = true;
		this.traversable = false;
	}
}
