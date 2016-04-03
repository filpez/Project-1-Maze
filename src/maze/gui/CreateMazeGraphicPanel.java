package maze.gui;

import java.awt.Graphics;
import java.awt.Point;

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

	public CreateMazeGraphicPanel(Maze maze){
		initialize();
		this.maze = maze;
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
			g.drawImage(HeroImage, 360, 15, 389, 44, 0, 0, HeroImage.getWidth(), HeroImage.getHeight(), null);
			g.drawImage(WallImage, 360, 75, 389, 104, 0, 0, WallImage.getWidth(), WallImage.getHeight(), null);
			g.drawImage(DragonImage, 360, 135, 389, 164, 0, 0, DragonImage.getWidth(), DragonImage.getHeight(), null);
			g.drawImage(ExitImage, 360, 195, 389, 224, 0, 0, ExitImage.getWidth(), ExitImage.getHeight(), null);
			g.drawImage(SwordImage, 360, 255, 389, 284, 0, 0, SwordImage.getWidth(), SwordImage.getHeight(), null);

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
