package highontoxins.fill;

import java.awt.Color;
import java.util.PriorityQueue;

import highontoxins.Main;
import highontoxins.Utill;
import highontoxins.gameObjects.tools.Vector;

public class Cell {

	//static fields
	private static float VARIATION = 0.25f; //general variation of color between each pixel. (255 being total change of color, and 0 being no change)
	private static float DETAIL = .01f; 
	
	private static int SPREAD = (int)(VARIATION/DETAIL);
	private static float CHANGE_CHANCE = DETAIL; // VARIATION/SPREAD simplifies to DETIAL
	
	public static Vector START_POSITION = Main.screenSize.div(4);
	public static float UPDATE_SPEED = 1f;
	
	private static final Vector[] PATHS = { //adjacent paths should be next to each other
//			new Vector(1, 1), //up-right
 			new Vector(1, 0), //right
// 			new Vector(1, -1), //down-right
 			new Vector(0, -1), //down
// 			new Vector(-1, -1), //down-left
 			new Vector(-1, 0), //left
// 			new Vector(-1, 1), //up-left
			new Vector(0, 1) //up 
	};
	
	private static Vector getRandomPath() {return PATHS[(int)(Math.random() * PATHS.length)];}
	
	private static Vector getAdjacentPath(Vector path) {
		//find path
		for(int i = 0; i < PATHS.length; i++) {
			if(path.equals(PATHS[i])) {
				//returns path adjacent (based on the vectors of PATHS being adjacent)
				return PATHS[(i + (int)(Math.random() < .5 ? -1 : +1) + PATHS.length) % PATHS.length];
			}
		}
		
		//returning null if the path was not found
		return null;
	}
	
	//fields
	private PriorityQueue<Vector> paths;
	private Vector position;
	private Color color;
	
	private Vector favoredPath;
	
	private float weight;
	
	public Cell(Vector position, Color color) {
		//setting default favorite path
		this.favoredPath = getRandomPath(); //random favored path
//		this.favouredPath = new Vector(0, 1); //
		
		//setting up possible paths
		paths = newPaths();
		
		this.position = position;
		this.color = color;
		this.weight = 0;
	}
	
	public Cell(Cell parent) {
		
		//setting favored path
//		this.favoredPath = parent.favoredPath; //same as the parent
//		this.favoredPath = PATHS[(int)(Math.random() * PATHS.length)]; //random direction
//		this.favoredPath = Math.random() < .8 ? getRandomPath() : parent.favoredPath; //chance of choosing a random path
		this.favoredPath = Math.random() < .8 ? getAdjacentPath(parent.favoredPath) : parent.favoredPath; //chance of choosing an adjacent path
		
		//setting up possible paths
		paths = newPaths();
		
		//getting path from parent
		this.position = parent.position.add(parent.paths.peek());
		parent.paths.remove();
		
		//setting weight
//		this.weight = parent.weight - 1; //circle/square
//		this.weight = parent.weight - (float)Math.random(); //circle
//		this.weight = parent.weight + (Math.random() < .001 ? 15f : -1f); //Crystals
		this.weight = parent.weight + (float)Math.random() - .79f; //fungus (eclid)
//		this.weight = parent.weight + (float)Math.random() - 0.87f; //fungus (manhatten)
//		this.weight = parent.weight + (float)Math.random() - .5f; //layers
		
		mutateColor(parent.color);
	}
	
	//new paths
	private PriorityQueue<Vector> newPaths(){
		PriorityQueue<Vector> paths = new PriorityQueue<>((v1, v2) -> {
			int a = (int)Utill.normalize(favoredPath.dot(v2) - favoredPath.dot(v1));
			return a != 0 ? a : (Math.random() < .5 ? 1 : -1);
		});
		for(int i = 0; i < PATHS.length; i++) paths.add(new Vector(PATHS[i]));
		return paths;
	}
	
	//get values
	public boolean noPathsLeft() {return paths.isEmpty();}
	public Vector getPosition() {return position;}
	public Color getColor() {return color;}
	public float getWeight() {return weight;}
	
	//setting the color based on the parent's color
	public void mutateColor(Color parentColor) {

		//adding to colorMaps
		int r = computeColor(parentColor.getRed());
		int g = computeColor(parentColor.getGreen());
		int b = computeColor(parentColor.getBlue());

		color = new Color(r, g, b);
	}
	
	private int computeColor(int prevColor) {
		//determining change of color
		int colorChange = 0;
		if(Math.random() < CHANGE_CHANCE) colorChange = (Math.random() < .5 ? 1 : -1) * SPREAD;
		
		//getting new color
		int newColor = prevColor + colorChange;
		return Math.min(255, Math.max(0, newColor));
	}
	
	//check paths
	public void updatePaths(int[][] idMap) {
		boolean stop = paths.isEmpty();
		
		//remove paths
		while(!stop){
			stop = true;
			
			int x2 = (int) (position.x() + paths.peek().x());
			int y2 = (int) (position.y() + paths.peek().y());
			
			if(x2 >= idMap.length || x2 < 0 || y2 >= idMap[0].length  || y2 < 0 || idMap[x2][y2] != 0) {
				paths.remove();
				stop = paths.isEmpty();
			}
		}
		
	}
	
}
