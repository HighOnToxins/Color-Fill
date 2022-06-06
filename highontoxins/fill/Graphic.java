package highontoxins.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.PriorityQueue;

import highontoxins.Main;
import highontoxins.Utill;

public class Graphic {
	
	//fields
	private Color[][] colorMap;
	private int[][] idMap;
	private PriorityQueue<Cell> cells;
	
	public Graphic() {
		reset();
	}
	
	//reset the graphic
	public void reset() {
		int screenScale = 2;
		
		//data structures
		colorMap = new Color[(int)Main.screenSize.x() / screenScale][(int)Main.screenSize.y() / screenScale];
		idMap = new int[colorMap.length][colorMap[0].length];
		cells = new PriorityQueue<Cell>((c1, c2) -> (int)(Utill.normalize(c2.getWeight() - c1.getWeight())));

		//adding starting cell
		addStartingCell(null);
	}
	
	public void addStartingCell(Color color) {
		if(color == null) color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
		cells.add(new Cell(Cell.START_POSITION, color));
		idMap[(int)Cell.START_POSITION.x()][(int)Cell.START_POSITION.y()] = 1;
	}
	
	//takes the next step of the visualization
	public void update() {
		int size = cells.size();
		for(int i = 0; i < size * Cell.UPDATE_SPEED; i++) {

			cells.peek().updatePaths(idMap);
			
			//choosing a path
			if(!cells.peek().noPathsLeft()) {
				//adding new cell
				Cell cell = new Cell(cells.peek());
				cells.add(cell);
				
				//setting maps
				colorMap[(int)cell.getPosition().x()][(int)cell.getPosition().y()] = cell.getColor();
				idMap[(int)cell.getPosition().x()][(int)cell.getPosition().y()] = 1;
			} 
			
			if(cells.peek().noPathsLeft()) cells.remove();
			
		}
	}
	
	public void drawColor(Graphics2D g) {
		
		float deltaX = (float) (Main.screenSize.x() / colorMap.length);
		float deltaY = (float) (Main.screenSize.y() / colorMap[0].length);
		
		for(int x = 0; x < colorMap.length; x++) {
			for(int y = 0; y < colorMap[0].length; y++) {
				
				switch(idMap[x][y]) {
				case 0: continue;
				default: g.setColor(colorMap[x][y]);
				}
				
				g.fillRect((int)(x * deltaX), (int)(y * deltaY), (int)deltaX, (int)deltaY);
				
			}
		}
		
	}
	
	//interface for lambda functions
	public interface Func{
		int chosePath(int pathCount); 
	}
}

