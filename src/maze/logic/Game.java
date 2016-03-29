package maze.logic;

public class Game {
	public enum State {RUNNING, WON, LOST}
	public enum Mode {STATIC, SLEEP_RANDOM, RANDOM}

	private State state = State.RUNNING;
	private Maze maze;
	private Mode mode;
	
	public Game(Mode mode){
		this.maze = CharBuilder.getNewMaze(11, 1);
		this.mode = mode;
	}
	
	
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}



	public void takeTurn(MazeLogic.Movement movDirection){
		state = MazeLogic.takeTurn(maze, movDirection, mode);
	}
}
