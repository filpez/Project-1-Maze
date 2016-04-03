package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.MazeLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphicInterface {

	private JFrame frame;
	private MazeGraphicPanel mazeDisplay;
	
	private Game game;
	private JButton UpButton;
	private JButton LeftButton;
	private JButton RightButton;
	private JButton DownButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicInterface window = new GraphicInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GraphicInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton GenerateMazeButton = new JButton("Generate New Maze");
		GenerateMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateMazeInterface generator = new GenerateMazeInterface(frame, true);
				Maze maze = generator.getMaze();
				Game.Mode mode = generator.getMode();
				initiateGame(maze, mode);
			}
		});
		GenerateMazeButton.setBounds(10, 25, 150, 46);
		frame.getContentPane().add(GenerateMazeButton);
		
		JButton FinishButton = new JButton("Finish");
		FinishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		FinishButton.setBounds(324, 25, 150, 46);
		frame.getContentPane().add(FinishButton);
		
		JButton CreateMazeButton = new JButton("Create New Maze");
		CreateMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateMazeInterface creator = new CreateMazeInterface(frame, true);
				Maze maze = creator.getMaze();
				Game.Mode mode = creator.getMode();
				initiateGame(maze, mode);
			}
		});
		CreateMazeButton.setBounds(170, 25, 144, 46);
		frame.getContentPane().add(CreateMazeButton);
		
		UpButton = new JButton("UP");
		UpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				takeTurn(MazeLogic.Movement.UP);
			}
		});
		UpButton.setEnabled(false);
		UpButton.setBounds(324, 182, 150, 46);
		frame.getContentPane().add(UpButton);
		
		LeftButton = new JButton("LEFT");
		LeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.LEFT);
			}
		});
		LeftButton.setEnabled(false);
		LeftButton.setBounds(324, 239, 70, 46);
		frame.getContentPane().add(LeftButton);
		
		RightButton = new JButton("RIGHT");
		RightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.RIGHT);
			}
		});
		RightButton.setEnabled(false);
		RightButton.setBounds(404, 239, 70, 46);
		frame.getContentPane().add(RightButton);
		
		DownButton = new JButton("DOWN");
		DownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.DOWN);
			}
		});
		DownButton.setEnabled(false);
		DownButton.setBounds(324, 296, 150, 46);
		frame.getContentPane().add(DownButton);
		
		
		
	}
	
	public void initiateGame(Maze maze, Game.Mode mode){
		if (maze != null){
			this.game = new Game(maze, mode);
			mazeDisplay = new MazeGraphicPanel(game, this);
			mazeDisplay.setBounds(10, 109, 300, 300);
			mazeDisplay.setVisible(true);
			frame.getContentPane().add(mazeDisplay);
			takeTurn(MazeLogic.Movement.NULL);
		
			DownButton.setEnabled(true);
			UpButton.setEnabled(true);
			LeftButton.setEnabled(true);
			RightButton.setEnabled(true);
			mazeDisplay.requestFocus();
		}
	}
	
	public void takeTurn(MazeLogic.Movement movDirection){
		game.takeTurn(movDirection);
		mazeDisplay.repaint();
		mazeDisplay.requestFocus();	
		if(game.getState() != Game.State.RUNNING){
			mazeDisplay.setEnabled(false);
			DownButton.setEnabled(false);
			UpButton.setEnabled(false);
			LeftButton.setEnabled(false);
			RightButton.setEnabled(false);
		}
	}
}
