import java.awt.Color;

/**
 * Immutable representation of a single dot the character can eat
 * @author Will
 * @version 1.0
 */
public class Dot {
	private int x;
	private int y;
	private static Color maroon = new Color(141, 2, 31);
	private static final Color color = maroon;
	/**
	 * Creates a dot at the specified x and y coordinate
	 * @param x x coordinate for the dot
	 * @param y y coordinate for the dot
	 */
	public Dot(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retrieve the x value
	 * @return the x value of the dot
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Retrieve the y value
	 * @return the y value of the dot
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Retrieve the color
	 * @return the color of the dot
	 */
	public Color getColor() {
		return color;
	}
}
