package maze.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author Filipe
 *
 */

public class Maze {
	private Hashtable<Point, ArrayList<Entity>> maze;
	private int dimension;
	private Point HeroKey = null;

	/**
	 * The maze should countain (dimension x dimension) entries.
	 * @param maze - initial maze
	 * @param dimension - size of the maze
	 */
	public Maze(Hashtable<Point, ArrayList<Entity>> maze, int dimension) {
		super();
		this.maze = maze;
		this.dimension = dimension;
		for (Point position: getAllPositions()){
			ArrayList<Entity> currentCell = maze.get(position);
			if (currentCell.size() > 0 && currentCell.get(0) instanceof Hero)
				HeroKey = position;
		}
	}

	/**
	 * @param position - wanted cell's coordinates 
	 * @return the list of entities in the cell
	 */
	public ArrayList<Entity> getCell(Point position){
		return maze.get(position);
	}
	
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public Point getHeroKey() {
		return HeroKey;
	}

	public void setHeroKey(Point heroKey) {
		HeroKey = heroKey;
	}

	/**
	 * @return the entire maze structure
	 */
	public Hashtable<Point, ArrayList<Entity>> getMaze() {
		return maze;
	}

	/**
	 * @param init - initial entity coordinates on the maze
	 * @param index - initial entity index on the list of entities at the cell corresponding to init
	 * @param dest - destination coordinates on the maze
	 */
	public void moveEntity(Point init,int index, Point dest){
		Entity temp = getCell(init).remove(index);
		if (temp instanceof Hero)
			HeroKey = dest;
		getCell(dest).add(temp);
		temp.setMoved(true);
		
	}

	/**
	 * @return all possible coordinates on the maze
	 */
	public ArrayList<Point> getAllPositions(){
		ArrayList<Point> allPositions = new ArrayList<Point>();
		for (int y = 0; y < dimension; y++){
			for (int x = 0; x < dimension; x++){
				allPositions.add(new Point(x,y));
			}
		}
		return allPositions;
	}

	/**
	 * @return current index of the Hero on the list of entities of the cell which cointains the hero
	 */
	public int getHeroIndex() {
		ArrayList<Entity> currentCell = maze.get(HeroKey);
		int index = 0;
		while(!(currentCell.get(index) instanceof Hero))
			index++;
		return index;
	}

	/**
	 * @return the Hero
	 */
	public Hero getHero() {
		return (Hero)getEntity(HeroKey,getHeroIndex());
	}
	
	/**
	 * @param position - entity coordinates on the maze
	 * @param index - entity index on the list of entities of cell corresponding to position
	 * @return the entity pointed by position and index
	 */
	public Entity getEntity(Point position, int index) {
		return maze.get(position).get(index);
	}
	
	/**
	 * Removes an entity from the maze.
	 * @param position - entity coordinates on the maze
	 * @param index - entity index on the list of entities of cell corresponding to position
	 * @return the entity pointed by position and index
	 */
	public Entity removeEntity(Point position, int index) {
		return maze.get(position).remove(index);
	}
	
	/**
	 * Checks if position is within the area of the maze
	 * @param position - coordinates on the maze
	 * @return true if position is valid, false otherwise
	 */
	public boolean validPoint(Point position){
		int x = position.x;
		int y = position.y;
		
		if (x < 0 || x >= dimension || y < 0 || y >= dimension)
			return false;
		return true;
	}
}