package maze.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import maze.logic.Dragon;
import maze.logic.Entity;
import maze.logic.Exit;
import maze.logic.Game;
import maze.logic.Hero;
import maze.logic.Maze;
import maze.logic.MazeLogic;
import maze.logic.Sword;
import maze.logic.Wall;


public class MazeGraphicPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BufferedImage WallImage;
	private static BufferedImage HeroImage;
	private static BufferedImage ArmedHeroImage;
	private static BufferedImage DragonImage;
	private static BufferedImage SwordImage;
	private static BufferedImage DragonSwordImage;
	private static BufferedImage ExitImage;
	private static BufferedImage FloorImage;

	private Game game;
	private GraphicInterface parentFrame;
	
	public void initialize(){
		try {
			WallImage =  ImageIO.read(new File("Wall.png"));
			HeroImage =  ImageIO.read(new File("Hero.png"));
			ArmedHeroImage =  ImageIO.read(new File("ArmedHero.png"));
			DragonImage =  ImageIO.read(new File("Dragon.png"));
			SwordImage =  ImageIO.read(new File("Sword.png"));
			DragonSwordImage =  ImageIO.read(new File("DragonSword.png"));
			ExitImage =  ImageIO.read(new File("Exit.png"));
			FloorImage =  ImageIO.read(new File("Floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public MazeGraphicPanel(Game game, GraphicInterface graphicInterface){
		initialize();

		this.game = game;
		this.parentFrame = graphicInterface;

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT: 
					parentFrame.takeTurn(MazeLogic.Movement.LEFT);
					break;

				case KeyEvent.VK_RIGHT: 
					parentFrame.takeTurn(MazeLogic.Movement.RIGHT);
					break;

				case KeyEvent.VK_UP: 
					parentFrame.takeTurn(MazeLogic.Movement.UP);
					break;

				case KeyEvent.VK_DOWN: 
					parentFrame.takeTurn(MazeLogic.Movement.DOWN);
					break;
				}
				repaint();


			}

			@Override
			public void keyReleased(KeyEvent e) {
			}			
		});
	}





	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (game != null){
			System.out.print(game != null);
			System.out.print("A");
			Maze maze = game.getMaze();
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
		ArrayList<Entity> cell = game.getMaze().getCell(position);
		int livingEntities = 0;
		for (Entity ent: cell)
			if (ent.getStatus() != Entity.Status.DEAD)
				livingEntities++;

		switch (livingEntities){
		case 0:
			g.drawImage(FloorImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, FloorImage.getWidth(), FloorImage.getHeight(), null);
			break;
		case 1:
			Entity livingEnt = cell.get(0);
			for (Entity ent: cell)
				if (ent.getStatus() != Entity.Status.DEAD)
					livingEnt = ent;
			
			if (livingEnt instanceof Hero){
				if (((Hero) livingEnt).isArmed())
					g.drawImage(ArmedHeroImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, ArmedHeroImage.getWidth(), ArmedHeroImage.getHeight(), null);
				else
					g.drawImage(HeroImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, HeroImage.getWidth(), HeroImage.getHeight(), null);
			}
			if (livingEnt instanceof Wall)
				g.drawImage(WallImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, WallImage.getWidth(), WallImage.getHeight(), null);
			if (livingEnt instanceof Dragon){
				if (livingEnt.getStatus() == Entity.Status.SLEEPING)
					g.drawImage(DragonImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, DragonImage.getWidth(), DragonImage.getHeight(), null);
				else
					g.drawImage(DragonImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, DragonImage.getWidth(), DragonImage.getHeight(), null);
			}
			if (livingEnt instanceof Exit)
				g.drawImage(ExitImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, ExitImage.getWidth(), ExitImage.getHeight(), null);
			if (livingEnt instanceof Sword)
				g.drawImage(SwordImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, SwordImage.getWidth(), SwordImage.getHeight(), null);
			break;
		case 2:
			g.drawImage(DragonSwordImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, DragonSwordImage.getWidth(), DragonSwordImage.getHeight(), null);
			break;
		default:
			g.drawImage(FloorImage, x, y, x + imageWidth - 1, y + imageHeight - 1, 0, 0, FloorImage.getWidth(), FloorImage.getHeight(), null);
			break;
		}
	}

	public void takeTurn(MazeLogic.Movement mov){
		game.takeTurn(mov);
		repaint();
	}
}
