package highontoxins;

import java.awt.Dimension;
import java.awt.Toolkit;

import highontoxins.gameObjects.tools.Vector;

public class Utill { 

	/***@return Returns the center of the computer screen.*/
	public static Vector getScreenCenter() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		return new Vector(screen.getWidth(), screen.getHeight()).sub(Main.windowSize).div(2);
	}
	
	/***
	 * @param d is any double value.
	 * @return 1 if d is positive -1 if it is negative and zero if it is positive.
	 */
	public static double normalize(double d) {return d == 0 ? 0 : d / Math.abs(d);}
	
}
