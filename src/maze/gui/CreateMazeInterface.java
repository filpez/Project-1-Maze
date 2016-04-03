package maze.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;

import maze.logic.Dragon;
import maze.logic.Entity;
import maze.logic.Exit;
import maze.logic.Game;
import maze.logic.Hero;
import maze.logic.Maze;
import maze.logic.Sword;
import maze.logic.Wall;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Point;


public class CreateMazeInterface extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField MazeDimension;
	private JComboBox<String> Mode;

	private Maze finalMaze = null;
	private Maze maze = null;
	private Game.Mode mode = null;
	private CreateMazeGraphicPanel panel;
	private JButton TerminateButton;

	/**
	 * Create the application.
	 */
	public CreateMazeInterface(JFrame frame, boolean modal) {
		super(frame, modal);
		initialize();
		setVisible(true);  
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 490, 447);
		getContentPane().setLayout(null);

		JLabel MazeDimensionLabel = new JLabel("Maze Dimension");

		MazeDimensionLabel.setBounds(11, 17, 84, 14);
		getContentPane().add(MazeDimensionLabel);

		Mode = new JComboBox<String>();
		Mode.setModel(new DefaultComboBoxModel<String>(new String[] {"Static", "Random and Sleeps", "Random"}));
		Mode.setBounds(341, 14, 119, 20);
		getContentPane().add(Mode);

		MazeDimension = new JTextField();
		MazeDimension.setText("11");
		MazeDimension.setColumns(10);
		MazeDimension.setBounds(105, 14, 119, 20);
		getContentPane().add(MazeDimension);

		JButton EmptyMazeButton = new JButton("Empty Maze");
		EmptyMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int dimension = Integer.parseInt(MazeDimension.getText());
				Hashtable<Point, ArrayList<Entity>> emptyMaze = new Hashtable<Point, ArrayList<Entity>>();
				for(int y = 0; y < dimension; y++)
					for(int x = 0; x < dimension; x++){
						Point p = new Point(x,y);
						ArrayList<Entity> list = new ArrayList<Entity>();
						if(x == 0 || y == 0 || x == dimension - 1 || y == dimension - 1)
							list.add(new Wall());
						emptyMaze.put(p, list);
					}
				maze = new Maze(emptyMaze, dimension);
				panel.setMaze(maze);
				panel.repaint();
				TerminateButton.setEnabled(true);
			}
		});

		JLabel label = new JLabel("Type of Dragons");
		label.setBounds(234, 16, 97, 14);
		getContentPane().add(label);
		EmptyMazeButton.setBounds(11, 359, 143, 42);
		getContentPane().add(EmptyMazeButton);

		JButton FillMazeButton = new JButton("Fill Maze");
		FillMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int dimension = Integer.parseInt(MazeDimension.getText());
				Hashtable<Point, ArrayList<Entity>> fillMaze = new Hashtable<Point, ArrayList<Entity>>();
				for(int y = 0; y < dimension; y++)
					for(int x = 0; x < dimension; x++){
						Point p = new Point(x,y);
						ArrayList<Entity> list = new ArrayList<Entity>();
						list.add(new Wall());
						fillMaze.put(p, list);
					}
				maze = new Maze(fillMaze, dimension);
				panel.setMaze(maze);
				panel.repaint();
				TerminateButton.setEnabled(true);
			}
		});
		FillMazeButton.setBounds(164, 359, 143, 42);
		getContentPane().add(FillMazeButton);

		TerminateButton = new JButton("Terminate");
		TerminateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validMaze()){
					finalMaze = maze;
					setVisible(false);
				}
			}
		});
		TerminateButton.setEnabled(false);
		TerminateButton.setBounds(317, 359, 143, 42);
		getContentPane().add(TerminateButton);

		panel = new CreateMazeGraphicPanel(maze);
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 48, 450, 300);
		getContentPane().add(panel);
	}


	public Game.Mode getMode() {
		return mode;
	}

	public Maze getMaze() {
		return finalMaze;
	}

	private boolean validMaze(){
		int heroCounter = 0;
		int swordCounter = 0;
		int dragonCounter = 0;
		int exitCounter = 0;

		for (Point position: maze.getAllPositions()){
			ArrayList<Entity> currentCell = maze.getCell(position);
			if (currentCell.size() > 0){
				Entity ent = currentCell.get(0);
				if (ent instanceof Hero)
					heroCounter++;
				if (ent instanceof Dragon)
					swordCounter++;
				if (ent instanceof Sword)
					dragonCounter++;
				if (ent instanceof Exit)
					exitCounter++;
			}		
		}

		if (heroCounter != 1){
			JOptionPane.showMessageDialog(this, "There must be exactly one hero.");
			return false;
		}
		if (exitCounter < 1){
			JOptionPane.showMessageDialog(this, "There must be at least one exit.");
			return false;
		}
		if (dragonCounter != 0 && swordCounter == 0){
			JOptionPane.showMessageDialog(this, "There must be at least one sword to kill dragons.");
			return false;
		}	
		return true;
	}
}
