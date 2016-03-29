package maze.logic;

import java.awt.Point;
import java.util.Random;
import java.util.Stack;

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

	public char[][] buildMaze(int size, int dragonCounter, int swordCounter) throws IllegalArgumentException{
		if(dragonCounter < 1)
			throw new IllegalArgumentException(new String("Number of Dragons must be bigger than 0.\n"));
		if(swordCounter < 1)
			throw new IllegalArgumentException(new String("Number of Dragons must be bigger than 0.\n"));
		this.dragonCounter = dragonCounter;
		this.swordCounter = swordCounter;
		return buildMaze(size);
	}
	
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

	private void initLab(){
		lab = new char[size][size];
		for(int i = 0; i < size; i++)
			for(int j = 0; j < size; j++)
				if(i % 2 == 1 && j % 2 == 1)
					lab[i][j] = ' ';
				else
					lab[i][j] = 'X';
	}

	private void initVisitedCells(){
		VCsize = (size-1)/2;
		visitedCells = new char[VCsize][VCsize];
		for(int i = 0; i < VCsize; i++)
			for(int j = 0; j < VCsize; j++)
				visitedCells[i][j] = '.';
	}

	private void initStartingPoint(){
		Random random = new Random();
		int x = random.nextInt(VCsize);
		int y = random.nextInt(VCsize);
		this.AtualPos = new Point(x,y);
		visitedCells[AtualPos.y][AtualPos.x] = '+';
	}

	private void initPathHistory(){
		pathHistory = new Stack<Point>();
		pathHistory.push(AtualPos);
	}

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
	
	private Direction getRandomDirection(){
		Random random = new Random();
		return Direction.values()[random.nextInt(Direction.values().length)];
	}

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

	private boolean isDeadEnd(){
		if(!isFree(Direction.UP) && !isFree(Direction.LEFT) && !isFree(Direction.RIGHT) && !isFree(Direction.DOWN))
			return true;
		return false;
	}


}
