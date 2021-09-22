/**
 * Holds 2 linked locations on the board
 * @author Will
 * @version 1.0
 */
public class Portal {
	int entrance1x;
	int entrance1y;
	int entrance2x;
	int entrance2y;
	/**
	 * 
	 * @param entrance1x x value of first portal
	 * @param entrance1y y value of first portal
	 * @param entrance2x x value of second portal
	 * @param entrance2y y value of second portal
	 */
	public Portal(int entrance1x, int entrance1y, int entrance2x, int entrance2y) {
		this.entrance1x = entrance1x;
		this.entrance1y = entrance1y;
		this.entrance2x = entrance2x;
		this.entrance2y = entrance2y;
	}
	
	/**
	 * Retrieve x value of first portal
	 * @return x value
	 */
	public int getFirstX() {
		return entrance1x;
	}
	
	/**
	 * Retrieve y value of first portal
	 * @return y value
	 */
	public int getFirstY() {
		return entrance1y;
	}
	
	/**
	 * Retrieve x value of second portal
	 * @return x value
	 */
	public int getSecondX() {
		return entrance2x;
	}
	
	/**
	 * Retrieve y value of second portal
	 * @return y value
	 */
	public int getSecondY() {
		return entrance2y;
	}
}
