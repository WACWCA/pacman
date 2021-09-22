
public class Cell {
	public int x;
	public int y;
	public boolean[] connected;
	public Cell[] next;
	public Cell[] group;
	public boolean filled;
	public boolean vertShiftable;
	public boolean horizShiftable;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.connected = new boolean[] {false, false, false, false}; 
	}
	
	public Cell(Cell c) {
		this(c.x, c.y);
		this.connected = new boolean[c.connected.length];
		for(int i = 0; i < this.connected.length; i++) {
			this.connected[i] = c.connected[i];
		}
		
		this.filled = c.filled;
		this.vertShiftable = c.vertShiftable;
		this.horizShiftable = c.horizShiftable;
		
	}
	
	public String toString() {
		return "(" + x + ", " + y + ") " + connected[0] + " " + connected[1] + " " + connected[2] + " " + connected[3] + " " ;
	}
}
