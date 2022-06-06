package highontoxins.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import highontoxins.Main;
import highontoxins.fill.Graphic;

public class Run extends State{
	//variables
	private Graphic graphic;
	private boolean resetGraphics;
	
	//Constructor
	public Run() {
		graphic = new Graphic();
	}
	
	//Updating play state
	@Override
	public void update(){
		
		graphic.update();
		
		if(resetGraphics) {
			resetGraphics = false;
			graphic.reset();
		}
		
	}
	
	//drawing play state
	@Override
	public void draw(Graphics2D g) {		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int)Main.screenSize.x(), (int)Main.screenSize.y());
		
		//drawing function
		graphic.drawColor(g);
//		graphic.drawWeights(g);
	}

	//generate new perlin noise
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Main.exitGame();
		}else if(e.getKeyCode() == KeyEvent.VK_SPACE){
			resetGraphics = true;
		}
	}
}
