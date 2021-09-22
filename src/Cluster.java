
public class Cluster {
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public final boolean vertShift;
	public final boolean horizShift;
	private int startRow;
	private int startCol;
	public int[][] cluster;
	public Cluster(Cell c) {
		vertShift = c.vertShiftable;
		horizShift = c.horizShiftable;
		startRow = c.y * 3;
		startCol = c.x * 3;
		int numrows = vertShift ? 5 : 4;
		int numcols = horizShift ? 3 : 4;
		if(startRow == 0 && startCol == 0) {
			System.out.println("HERE");
			System.out.println(c);
		}
		cluster = new int[numrows][numcols];
		int filler = 0;
		if(!(c.filled)) {
			filler = -1;
		}
		for(int row = 0; row < cluster.length; row++) {
			for(int col = 0; col < cluster[0].length; col++) {
				cluster[row][col] = filler;
			}
		}
		if(c.connected[UP] == false && filler != -1) {
			for(int col = 0; col < cluster[0].length; col++) {
				cluster[0][col] = 1;
			}
		}
		if(c.connected[RIGHT] == false && filler != -1) {
			for(int row = 0; row < cluster.length; row++) {
				cluster[row][cluster[0].length - 1] = 1;
			}
		}
		if(c.connected[DOWN] == false && filler != -1) {
			for(int col = 0; col < cluster[0].length; col++) {
				cluster[cluster.length - 1][col] = 1;
			}
		}
		if(c.connected[LEFT] == false && filler != -1) {
			for(int row = 0; row < cluster.length; row++) {
				cluster[row][0] = 1;
			}
		}
	}
	
	public int getStartRow() {
		return startRow;
	}
	public void increaseStartRow() {
		startRow++;
	}
	
	public int getStartCol() {
		return startCol;
	}
	
	public void decreaseStartCol() {
		startCol--;
	}
	
	public boolean isEmptyCorner(int r, int c) {
		if((r == 0 || r == cluster.length - 1) && (c == 0 || c == cluster[0].length)) {
			if(cluster[r][c] == 0) {
				return true;
			}
		}
		return false;
	}
}
