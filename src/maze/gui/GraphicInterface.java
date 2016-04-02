package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

import maze.logic.Game;
import maze.logic.Maze;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphicInterface {

	private JFrame frame;
	private JPanel mazeDisplay;

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
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton GenerateMazeButton = new JButton("Generate New Maze");
		GenerateMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GenerateMazeInterface generator = new GenerateMazeInterface(frame, true);
				Maze maze = generator.getMaze();
				Game.Mode mode = generator.getMode();
				if (maze != null){
					mazeDisplay = new MazeGraphicPanel(maze, mode, mazeDisplay.getBounds().getWidth());
				}
				
			}
		});
		GenerateMazeButton.setBounds(10, 25, 150, 46);
		frame.getContentPane().add(GenerateMazeButton);
		
		JButton FinishButton = new JButton("Finish");
		FinishButton.setBounds(324, 25, 150, 46);
		frame.getContentPane().add(FinishButton);
		
		JButton CreateMazeButton = new JButton("Create New Maze");
		CreateMazeButton.setBounds(170, 25, 144, 46);
		frame.getContentPane().add(CreateMazeButton);
		
		JButton UpButton = new JButton("UP");
		UpButton.setEnabled(false);
		UpButton.setBounds(324, 182, 150, 46);
		frame.getContentPane().add(UpButton);
		
		JButton LeftButton = new JButton("LEFT");
		LeftButton.setEnabled(false);
		LeftButton.setBounds(324, 239, 70, 46);
		frame.getContentPane().add(LeftButton);
		
		JButton RightButton = new JButton("RIGHT");
		RightButton.setEnabled(false);
		RightButton.setBounds(404, 239, 70, 46);
		frame.getContentPane().add(RightButton);
		
		JButton DownButton = new JButton("DOWN");
		DownButton.setEnabled(false);
		DownButton.setBounds(324, 296, 150, 46);
		frame.getContentPane().add(DownButton);
		
		mazeDisplay = new JPanel();
		mazeDisplay.setBounds(10, 109, 300, 300);
		frame.getContentPane().add(mazeDisplay);
	}
}
