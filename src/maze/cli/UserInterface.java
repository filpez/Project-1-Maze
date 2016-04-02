package maze.cli;

import java.awt.Point;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;

import maze.logic.CharBuilder;
import maze.logic.Dragon;
import maze.logic.Entity;
import maze.logic.Exit;
import maze.logic.Game;
import maze.logic.Hero;
import maze.logic.Maze;
import maze.logic.MazeLogic;
import maze.logic.Sword;
import maze.logic.Wall;

public class UserInterface {

	private static Scanner in;
	private static final String WIN_MESSAGE = "WON!\n";
	private static final String LOST_MESSAGE = "LOST!\n";

	public static void main(String args[]){
		in = new Scanner(System.in);
		Game game;
		
		while(true){
			try{
				int dimension = askMazeSize();
				int numberOfDragons = askDragonNumber();
				Game.Mode mode = askMode();
				Maze maze = CharBuilder.getMaze(dimension, numberOfDragons);
				game = new Game(maze, mode);
				break;
			}
			catch (IllegalArgumentException e){
				System.out.println(e.getMessage());
			}
		}


		loop:	while(true){
			display(game);
			switch(game.getState()){
			case RUNNING:
				MazeLogic.Movement movDirection = askDirection();
				game.takeTurn(movDirection);
				break;
			case WON:
				System.out.println(WIN_MESSAGE);
				break loop;
			case LOST:
				System.out.println(LOST_MESSAGE);
				break loop;
			default:
				break loop;
			}
		}	
	}

	private static int askMazeSize(){
		int i;
		System.out.println("Maze size: ");
		i = in.nextInt();
		in.nextLine();
		return i;
	}

	private static int askDragonNumber(){
		int i;
		System.out.println("Number of Dragons: ");
		i = in.nextInt();
		in.nextLine();
		return i;
	}

	private static Game.Mode askMode(){
		String answer = new String();
		while(true){
			System.out.println("Choose Difficulty(Easy, Medium, Hard): ");
			answer = in.nextLine();
			switch(answer){
			case "Easy":
				return Game.Mode.STATIC;
			case "Medium":
				return Game.Mode.SLEEP_RANDOM;
			case "Hard":
				return Game.Mode.RANDOM;
			default:
				System.out.println("Invalid difficulty.\n");
				break;
			}
		}
	}

	private static MazeLogic.Movement askDirection(){
		char key;
		MazeLogic.Movement MovDirection = MazeLogic.Movement.NULL;
		while(MovDirection == MazeLogic.Movement.NULL){
			key = in.next().charAt(0);
			switch(key){
			case 'w':
				MovDirection = MazeLogic.Movement.UP;
				break;
			case 's':
				MovDirection = MazeLogic.Movement.DOWN;
				break;
			case 'a':
				MovDirection = MazeLogic.Movement.LEFT;
				break;
			case 'd':
				MovDirection = MazeLogic.Movement.RIGHT;
				break;
			default:
				System.out.println("Press W, A, S or D.\n");
				break;
			}
		}
		//in.close();
		return MovDirection;
	}


	public static void display(Game game){
		String str = "";
		Maze maze = game.getMaze();
		for (Point position: maze.getAllPositions()){
			ArrayList<Entity> currentCell = maze.getCell(position);
			char c = getChar(currentCell);
			str += c;
			str += " ";
			if (position.x + 1 == maze.getDimension())
				str += "\n";
			
		}
		System.out.println(str);
	}

	private static char getChar(ArrayList<Entity> cell) {
		int livingEntities = 0;
		for (Entity ent: cell)
			if (ent.getStatus() != Entity.Status.DEAD)
				livingEntities++;
		
		switch (livingEntities){
		case 0:
			return ' ';
		case 1:
			Entity livingEnt = cell.get(0);
			for (Entity ent: cell)
				if (ent.getStatus() != Entity.Status.DEAD)
					livingEnt = ent;
			if (livingEnt instanceof Hero){
				if (((Hero) livingEnt).isArmed())
					return 'A';
				else
					return 'H';
			}
			if (livingEnt instanceof Wall)
				return 'X';
			if (livingEnt instanceof Dragon){
				if (livingEnt.getStatus() == Entity.Status.SLEEPING)
					return 'd';
				else
					return 'D';
			}
			if (livingEnt instanceof Exit)
				return 'S';
			if (livingEnt instanceof Sword)
				return 'E';
		case 2:
			return 'F';
		default:
			return '+';
		}
	}
}
