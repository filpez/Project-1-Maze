package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import maze.logic.Game;
import maze.logic.Maze;
import maze.logic.MazeLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class GraphicInterface {

	private JFrame frmMaze;
	private MazeGraphicPanel mazeDisplay;
	
	private Game game;
	private JButton UpButton;
	private JButton LeftButton;
	private JButton RightButton;
	private JButton DownButton;
	private JLabel StateLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicInterface window = new GraphicInterface();
					window.frmMaze.setVisible(true);
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
		frmMaze = new JFrame();
		frmMaze.setTitle("MaZe");
		frmMaze.setResizable(false);
		frmMaze.setBounds(100, 100, 500, 500);
		frmMaze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMaze.getContentPane().setLayout(null);
		
		JButton GenerateMazeButton = new JButton("Generate New Maze");
		GenerateMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateMazeInterface generator = new GenerateMazeInterface(frmMaze, true);
				Maze maze = generator.getMaze();
				Game.Mode mode = generator.getMode();
				initiateGame(maze, mode);
			}
		});
		GenerateMazeButton.setBounds(10, 25, 150, 46);
		frmMaze.getContentPane().add(GenerateMazeButton);
		
		JButton FinishButton = new JButton("Finish");
		FinishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		FinishButton.setBounds(324, 25, 150, 46);
		frmMaze.getContentPane().add(FinishButton);
		
		JButton CreateMazeButton = new JButton("Create New Maze");
		CreateMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateMazeInterface creator = new CreateMazeInterface(frmMaze, true);
				Maze maze = creator.getMaze();
				Game.Mode mode = creator.getMode();
				initiateGame(maze, mode);
			}
		});
		CreateMazeButton.setBounds(170, 25, 144, 46);
		frmMaze.getContentPane().add(CreateMazeButton);
		
		UpButton = new JButton("UP");
		UpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				takeTurn(MazeLogic.Movement.UP);
			}
		});
		UpButton.setEnabled(false);
		UpButton.setBounds(324, 182, 150, 46);
		frmMaze.getContentPane().add(UpButton);
		
		LeftButton = new JButton("LEFT");
		LeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.LEFT);
			}
		});
		LeftButton.setEnabled(false);
		LeftButton.setBounds(324, 239, 70, 46);
		frmMaze.getContentPane().add(LeftButton);
		
		RightButton = new JButton("RIGHT");
		RightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.RIGHT);
			}
		});
		RightButton.setEnabled(false);
		RightButton.setBounds(404, 239, 70, 46);
		frmMaze.getContentPane().add(RightButton);
		
		DownButton = new JButton("DOWN");
		DownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeTurn(MazeLogic.Movement.DOWN);
			}
		});
		DownButton.setEnabled(false);
		DownButton.setBounds(324, 296, 150, 46);
		frmMaze.getContentPane().add(DownButton);
		
		StateLabel = new JLabel("Create or generate a new maze...");
		StateLabel.setBounds(10, 446, 464, 14);
		frmMaze.getContentPane().add(StateLabel);
		
		
		
	}
	
	public void initiateGame(Maze maze, Game.Mode mode){
		if (maze != null){
			this.game = new Game(maze, mode);
			mazeDisplay = new MazeGraphicPanel(game, this);
			mazeDisplay.setBounds(10, 109, 300, 300);
			mazeDisplay.setVisible(true);
			frmMaze.getContentPane().add(mazeDisplay);
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
			if (game.getState() == Game.State.WON)
				StateLabel.setText("You got to the exit!!!");
			else
				StateLabel.setText("You got killed...");
		}
		else
			StateLabel.setText("Move using the arrow keys or the buttons.");
	}
}
