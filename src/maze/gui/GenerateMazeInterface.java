package maze.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;

import maze.logic.CharBuilder;
import maze.logic.Game;
import maze.logic.Maze;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GenerateMazeInterface extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField NumberOfDragons;
	private JTextField MazeDimension;
	private JComboBox<String> Mode;

	private Maze maze = null;
	private Game.Mode mode = null;

	/**
	 * Create the application.
	 */
	public GenerateMazeInterface(JFrame frame, boolean modal) {
		super(frame, modal);
		initialize();
		setVisible(true);  
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 267, 208);
		getContentPane().setLayout(null);

		JLabel MazeDimensionLabel = new JLabel("Maze Dimension");

		MazeDimensionLabel.setBounds(10, 53, 97, 14);
		getContentPane().add(MazeDimensionLabel);

		JLabel NumberOfDragonsLabel = new JLabel("Number of Dragons");
		NumberOfDragonsLabel.setBounds(10, 16, 97, 14);
		getContentPane().add(NumberOfDragonsLabel);

		Mode = new JComboBox<String>();
		Mode.setModel(new DefaultComboBoxModel<String>(new String[] {"Static", "Random and Sleeps", "Random"}));
		Mode.setBounds(117, 88, 119, 20);
		getContentPane().add(Mode);

		NumberOfDragons = new JTextField();
		NumberOfDragons.setText("1");
		NumberOfDragons.setColumns(10);
		NumberOfDragons.setBounds(117, 13, 119, 20);
		getContentPane().add(NumberOfDragons);

		MazeDimension = new JTextField();
		MazeDimension.setText("11");
		MazeDimension.setColumns(10);
		MazeDimension.setBounds(117, 50, 119, 20);
		getContentPane().add(MazeDimension);

		JButton GenerateButton = new JButton("Generate Maze");
		GenerateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int dimension = Integer.parseInt(MazeDimension.getText());
					int dragons = Integer.parseInt(NumberOfDragons.getText());
					mode = Game.Mode.values()[Mode.getSelectedIndex()];
					maze = CharBuilder.getMaze(dimension, dragons);
				}
				catch (IllegalArgumentException ex){
					JOptionPane.showMessageDialog(GenerateMazeInterface.this, ex.getMessage());
					return;
				}
				setVisible(false);
			}
		});

		JLabel label = new JLabel("Type of Dragons");
		label.setBounds(10, 90, 97, 14);
		getContentPane().add(label);
		GenerateButton.setBounds(43, 119, 158, 42);
		getContentPane().add(GenerateButton);
	}


	public Game.Mode getMode() {
		return mode;
	}

	public Maze getMaze() {
		return maze;
	}


}
