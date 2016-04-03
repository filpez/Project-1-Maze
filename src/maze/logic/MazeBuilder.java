package maze.logic;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

/**
 * MazeBuilder generates random bi-dimensional arrays of chars which can be used to build mazes.
 *
 */
public class MazeBuilder implements IMazeBuilder{
	private enum Direction {UP, DOWN, LEFT, RIGHT}
	
	private int dragonCounter = 1;
	private int swordCounter = 1;
	private int size;
	private int VCsize;
	private Stack<Point> pathHistory;
	private Point AtualPos;
	private char lab[][];
	private char visitedCells[][];

	/**
	 * Builds a bi-dimensional array which represents square maze with "size" cells of side. Then places the selected number of swords and dragons inside the maze.
	 * @param size - how many cells the maze will have on each side.
	 * @param dragonCounter - how many dragon should be placed.
	 * @param swordCounter - how many sword should be placed.
	 * @return a bi-dimensional array which represents square maze.
	 * @throws IllegalArgumentException - See {@link #buildMaze(int size)}
	 */
	public char[][] buildMaze(int size, int dragonCounter, int swordCounter) throws IllegalArgumentException{
		if(dragonCounter < 1)
			throw new IllegalArgumentException(new String("Number of Dragons must be bigger than 0.\n"));
		if(swordCounter < 1)
			throw new IllegalArgumentException(new String("Number of Dragons must be bigger than 0.\n"));
		this.dragonCounter = dragonCounter;
		this.swordCounter = swordCounter;
		return buildMaze(size);
	}
	
	/**
	 * Builds a bi-dimensional array which represents square maze with "size" cells of side.
	 * Places inside the maze swords and dragons the private members dragonCounter and swordCounter which number is 1 by default.
	 * @param size - how many cells the maze will have on each side.
	 * @return a bi-dimensional array which represents square maze.
	 * @throws IllegalArgumentException - See {@link #buildMaze(int size)}
	 */
	public char[][] buildMaze(int size) throws IllegalArgumentException {
		if(size < 3 *dragonCounter)
			throw new IllegalArgumentException(new String("Size must be bigger than 3 times the number of dragons.\n"));
		if(size % 2 == 0)
			throw new IllegalArgumentException(new String("Size must be odd.\n"));
		this.size = size;

		initLab();
		initVisitedCells();
		initStartingPoint();
		initPathHistory();
		addExit();
		while(!pathHistory.isEmpty()){
			if (isDeadEnd())
				AtualPos = pathHistory.pop();
			else{
				Direction dir = getRandomDirection();
				if(isFree(dir)){
					move(dir);
					pathHistory.push(AtualPos);
				}
			}
		}
		
		addChar('H');
		for(int i = 0; i < dragonCounter; i++)
			addChar('D');
		for(int i = 0; i < swordCounter; i++)
		addChar('E');

		return lab;
	}

	/**
	 * Initiates lab to a bi-dimensional array of chars.
	 * If either or both the coordinates are odd, the corresponding char is set to 'X'. Otherwise, ' ' is used.
	 * Example:
	 *XXXXX
	 *X X X
	 *XXXXX
	 *X X X
	 *XXXXX
	 */
	private void initLab(){
		lab = new char[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				if(i % 2 == 1 && j % 2 == 1)
					lab[i][j] = ' ';
				else
					lab[i][j] = 'X';
	}

	/**
	 * Initiates VCsize and visitedCells.
	 */
	private void initVisitedCells(){
		VCsize = (size-1)/2;
		visitedCells = new char[VCsize][VCsize];
		for(int i = 0; i < VCsize; i++)
			for(int j = 0; j < VCsize; j++)
				visitedCells[i][j] = '.';
	}

	/**
	 * Generates a ramdom position to start the algorithm.
	 */
	private void initStartingPoint(){
		Random random = new Random();
		int x = random.nextInt(VCsize);
		int y = random.nextInt(VCsize);
		this.AtualPos = new Point(x,y);
		visitedCells[AtualPos.y][AtualPos.x] = '+';
	}

	/**
	 * Initiates PathHistory
	 */
	private void initPathHistory(){
		pathHistory = new Stack<Point>();
		pathHistory.push(AtualPos);
	}

	/**
	 * Generates a new Exit on lab
	 */
	private void addExit(){
		Random random = new Random();
		Direction dir = Direction.values()[random.nextInt(Direction.values().length)];
		int x = 1;
		int y = 0;
		switch(dir){
		case UP:
			x = random.nextInt(VCsize);
			y = 0;
			break;
		case DOWN:
			x = random.nextInt(VCsize);
			y = VCsize-1;
			break;
		case LEFT:
			x = 0;
			y = random.nextInt(VCsize);
			break;
		case RIGHT:
			x = VCsize-1;
			y = random.nextInt(VCsize);
			break;
		}
		
		
		switch(dir){
		case UP:
			lab[y*2][x*2+1] = 'S';
			break;
		case DOWN:
			lab[y*2+2][x*2+1] = 'S';
			break;
		case LEFT:
			lab[y*2+1][x*2] = 'S';
			break;
		case RIGHT:
			lab[y*2+1][x*2+2] = 'S';
			break;
		}
	}

	/**
	 * Adds a char to a random free position
	 * @param c - Char to be added
	 */
	private void addChar(char c){
		Random random = new Random();
		int x = random.nextInt(VCsize);
		int y = random.nextInt(VCsize);
		while(visitedCells[y][x] != '+'){
			x = random.nextInt(VCsize);
			y = random.nextInt(VCsize);
		}
		visitedCells[y][x] = c;
		lab[y*2+1][x*2+1] = c;
	}
	
	/**
	 * Generates a random direction
	 * @return - a random direction
	 */
	private Direction getRandomDirection(){
		Random random = new Random();
		return Direction.values()[random.nextInt(Direction.values().length)];
	}

	/**
	 * Moves the actual position in a direction
	 * @param dir - the chosen direction
	 */
	private void move(Direction dir){
		int x = AtualPos.x;
		int y = AtualPos.y;
		switch(dir){
		case UP:
			lab[y*2][x*2+1] = ' ';
			y -= 1;
			break;
		case DOWN:
			lab[y*2+2][x*2+1] = ' ';
			y += 1;
			break;
		case LEFT:
			lab[y*2+1][x*2] = ' ';
			x -= 1;
			break;
		case RIGHT:
			lab[y*2+1][x*2+2] = ' ';
			x += 1;
			break;
		}
		visitedCells[y][x] = '+';
		AtualPos = new Point(x,y);
	}

	/**
	 * Checks if a selected direction is free to move to.
	 * @param dir -  the chosen direction
	 * @return true if it is free, false otherwise
	 */
	private boolean isFree(Direction dir){
		int x = AtualPos.x;
		int y = AtualPos.y;
		switch(dir){
		case UP:
			y -= 1;
			break;
		case DOWN:
			y += 1;
			break;
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		}
		if (x < 0 || x >= VCsize)
			return false;
		if (y < 0 || y >= VCsize)
			return false;
		if (visitedCells[y][x] == '+')
			return false;
		return true;
	}

	/**
	 * Checks if there is still a free direction to move to.
	 * @return false if all direction are not free, true otherwise.
	 */
	private boolean isDeadEnd(){
		if(!isFree(Direction.UP) && !isFree(Direction.LEFT) && !isFree(Direction.RIGHT) && !isFree(Direction.DOWN))
			return true;
		return false;
	}
}
