package maze.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze.logic.Dragon;
import maze.logic.Entity;
import maze.logic.Exit;
import maze.logic.Hero;
import maze.logic.Maze;
import maze.logic.Sword;
import maze.logic.Wall;


public class CreateMazeGraphicPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BufferedImage WallImage;
	private static BufferedImage HeroImage;
	private static BufferedImage DragonImage;
	private static BufferedImage SwordImage;
	private static BufferedImage ExitImage;
	private static BufferedImage FloorImage;

	private Maze maze;
	private CreateMazeInterface createMazeInterface;

	public void initialize(){
		try {
			WallImage =  ImageIO.read(new File("Wall.png"));
			HeroImage =  ImageIO.read(new File("Hero.png"));
			DragonImage =  ImageIO.read(new File("Dragon.png"));
			SwordImage =  ImageIO.read(new File("Sword.png"));
			ExitImage =  ImageIO.read(new File("Exit.png"));
			FloorImage =  ImageIO.read(new File("Floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public CreateMazeGraphicPanel(CreateMazeInterface createMazeInterface, Maze maze){
		initialize();
		this.createMazeInterface = createMazeInterface;
		this.maze = maze;
		
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Maze maze = CreateMazeGraphicPanel.this.maze;
				int dimension = maze.getDimension();
				int imageLateralSize = (int)(CreateMazeGraphicPanel.this.getHeight()/dimension);
				int x = e.getX()/imageLateralSize;
				int y = e.getY()/imageLateralSize;
				Point p = new Point(x,y);
				if (maze.validPoint(p))
					putEntityAtCell(p);	
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
	}


	

	protected void putEntityAtCell(Point p) {
		ArrayList<Entity> cell = maze.getCell(p);
		Entity ent = createMazeInterface.getSelectedEntity();
		cell.clear();
		if (ent != null)
			cell.add(ent);
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (maze != null){
			int imageLateralSize = (int)(this.getHeight()/maze.getDimension());
			for (Point position: maze.getAllPositions()){
				drawCell(g, position, imageLateralSize);
			}


		}
		
	}


	private void drawCell(Graphics g, Point position, int imageLateralSize) {
		int imageHeight = imageLateralSize;
		int imageWidth = imageLateralSize;
		int x = position.x*imageWidth;
		int y = position.y*imageHeight;
		ArrayList<Entity> cell = maze.getCell(position);

		switch (cell.size()){
		case 0:
			g.drawImage(FloorImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, FloorImage.getWidth(), FloorImage.getHeight(), null);
			break;
		case 1:
			Entity livingEnt = cell.get(0);

			if (livingEnt instanceof Hero)
				g.drawImage(HeroImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, HeroImage.getWidth(), HeroImage.getHeight(), null);
			if (livingEnt instanceof Wall)
				g.drawImage(WallImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, WallImage.getWidth(), WallImage.getHeight(), null);
			if (livingEnt instanceof Dragon)
				g.drawImage(DragonImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, DragonImage.getWidth(), DragonImage.getHeight(), null);
			if (livingEnt instanceof Exit)
				g.drawImage(ExitImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, ExitImage.getWidth(), ExitImage.getHeight(), null);
			if (livingEnt instanceof Sword)
				g.drawImage(SwordImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, SwordImage.getWidth(), SwordImage.getHeight(), null);
			break;
		default:
			g.drawImage(FloorImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, FloorImage.getWidth(), FloorImage.getHeight(), null);
			break;
		}
	}
}
