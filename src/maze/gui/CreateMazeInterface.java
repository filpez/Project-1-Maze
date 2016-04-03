package maze.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
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
import java.awt.Point;
import javax.swing.JRadioButton;


public class CreateMazeInterface extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField MazeDimension;
	private JComboBox<String> Mode;
	private CreateMazeGraphicPanel panel;
	private JButton TerminateButton;
	private ButtonGroup EntitySelection;

	private Maze finalMaze = null;
	private Maze maze = null;
	private Game.Mode mode = null;
	private JRadioButton HeroButton;
	private JRadioButton DragonButton;
	private JRadioButton SwordButton;
	private JRadioButton WallButton;
	private JRadioButton ExitButton;
	private JRadioButton FloorButton;

	/**
	 * Create the application.
	 */
	public CreateMazeInterface(JFrame frame, boolean modal) {
		super(frame, modal);
		setTitle("Create Maze");
		initialize();
		setVisible(true);  
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 490, 417);
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
				initiateDrawingBoard(false);
			}
		});

		JLabel label = new JLabel("Type of Dragons");
		label.setBounds(234, 16, 97, 14);
		getContentPane().add(label);
		EmptyMazeButton.setBounds(317, 222, 143, 42);
		getContentPane().add(EmptyMazeButton);

		JButton FillMazeButton = new JButton("Fill Maze");
		FillMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initiateDrawingBoard(true);
			}
		});
		FillMazeButton.setBounds(317, 275, 143, 42);
		getContentPane().add(FillMazeButton);

		TerminateButton = new JButton("Terminate");
		TerminateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validMaze()){
					mode = Game.Mode.values()[Mode.getSelectedIndex()];
					finalMaze = new Maze(maze.getMaze(), maze.getDimension());
					setVisible(false);
				}
			}
		});
		TerminateButton.setEnabled(false);
		TerminateButton.setBounds(317, 328, 143, 42);
		getContentPane().add(TerminateButton);



		HeroButton = new JRadioButton("Hero");
		HeroButton.setSelected(true);
		HeroButton.setBounds(317, 62, 109, 23);
		getContentPane().add(HeroButton);

		DragonButton = new JRadioButton("Dragon");
		DragonButton.setBounds(317, 88, 109, 23);
		getContentPane().add(DragonButton);

		SwordButton = new JRadioButton("Sword");
		SwordButton.setBounds(317, 114, 109, 23);
		getContentPane().add(SwordButton);

		WallButton = new JRadioButton("Wall");
		WallButton.setBounds(317, 140, 109, 23);
		getContentPane().add(WallButton);

		ExitButton = new JRadioButton("Exit");
		ExitButton.setBounds(317, 166, 109, 23);
		getContentPane().add(ExitButton);

		FloorButton = new JRadioButton("Floor");
		FloorButton.setBounds(317, 192, 109, 23);
		getContentPane().add(FloorButton);

		EntitySelection = new ButtonGroup();
		EntitySelection.add(HeroButton);
		EntitySelection.add(DragonButton);
		EntitySelection.add(SwordButton);
		EntitySelection.add(WallButton);
		EntitySelection.add(ExitButton);
		EntitySelection.add(FloorButton);

		JLabel lblSelectTypeOf = new JLabel("Select Type of Entity:");
		lblSelectTypeOf.setBounds(317, 41, 119, 14);
		getContentPane().add(lblSelectTypeOf);
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
			int x = position.x;
			int y = position.y;
			int dimension = maze.getDimension();
			ArrayList<Entity> currentCell = maze.getCell(position);
			if (currentCell.size() > 0){
				Entity ent = currentCell.get(0);
				if (ent instanceof Hero)
					heroCounter++;
				if (ent instanceof Sword)
					swordCounter++;
				if (ent instanceof Dragon)
					dragonCounter++;
				if (ent instanceof Exit)
					exitCounter++;
			}
			if(x == 0 || y == 0 || x == dimension - 1 || y == dimension - 1){
				if (currentCell.size() == 0){
					JOptionPane.showMessageDialog(this, "The sides of the maze must have either a exit or a wall.");
					return false;
				}
				else{
					Entity ent = currentCell.get(0);
					if (!(ent instanceof Exit) && !(ent instanceof Wall)){
						JOptionPane.showMessageDialog(this, "The sides of the maze must have either a exit or a wall.");
						return false;
					}	
				}
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

	public Entity getSelectedEntity() {
		if(HeroButton.isSelected())
			return new Hero();
		if(WallButton.isSelected())
			return new Wall();
		if(SwordButton.isSelected())
			return new Sword();
		if(ExitButton.isSelected())
			return new Exit();
		if(DragonButton.isSelected())
			return new Dragon();
		if(FloorButton.isSelected())
			return null;
		return null;
	}

	private void initiateDrawingBoard(boolean fill){
		if (panel != null)
			remove(panel);
		panel = new CreateMazeGraphicPanel(this, maze);
		panel.setEnabled(false);
		panel.setBounds(11, 61, 300, 300);
		getContentPane().add(panel);

		int dimension = Integer.parseInt(MazeDimension.getText());
		Hashtable<Point, ArrayList<Entity>> emptyMaze = new Hashtable<Point, ArrayList<Entity>>();
		for(int y = 0; y < dimension; y++)
			for(int x = 0; x < dimension; x++){
				Point p = new Point(x,y);
				ArrayList<Entity> list = new ArrayList<Entity>();
				if(!fill){
					if(x == 0 || y == 0 || x == dimension - 1 || y == dimension - 1)
						list.add(new Wall());
				}
				else
					list.add(new Wall());
				emptyMaze.put(p, list);
			}
		maze = new Maze(emptyMaze, dimension);
		panel.setMaze(maze);
		panel.setEnabled(true);
		panel.repaint();

		TerminateButton.setEnabled(true);
	}
}
