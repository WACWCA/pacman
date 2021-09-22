import java.util.ArrayList;

public class Piece {
	public Cell[][] cells;
	public int rowOffset;
	public String name;
	public ArrayList<Cell> vertShiftable;
	public ArrayList<Cell> horizShiftable;
	public Piece(int numRows, int numCols) {
		vertShiftable = new ArrayList<>();
		horizShiftable = new ArrayList<>();
		cells = new Cell[numRows][numCols];
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
				cells[row][col] = new Cell(col, row);
			}
		}
	}
	
	public Piece(Piece p) {
		this(p.cells.length, p.cells[0].length);
		for(int row = 0; row < cells.length; row++) {
			for(int col = 0; col < cells[0].length; col++) {
				this.cells[row][col] = p.cells[row][col];
			}
		}
		this.vertShiftable = new ArrayList<>(p.vertShiftable);
		this.horizShiftable = new ArrayList<>(p.horizShiftable);
		
	}
	
	public void addVert(Cell c) {
		vertShiftable.add(c);
		c.vertShiftable = true;
	}
	public void addHoriz(Cell c) {
		vertShiftable.add(c);
		c.horizShiftable = true;
	}
	public boolean canBePlaced(Maze m, int rowStart, int colStart) {
		if((rowStart == 7 && colStart == 0 && rowOffset == 1) && cells[0][0].filled == true) {
			return false;
		}
		
		if((rowStart == 6 && colStart == 0 && rowOffset == 0) && cells[1][0].filled == true) {
			return false;
		}
		try{
			for(int row = 0; row < cells.length; row++){
				for(int col = 0; col < cells[0].length; col++) {
					if(cells[row][col].filled == true 
							&& m.cells[rowStart - rowOffset + row][colStart + col].filled == true) {
						return false;
					}
				}
			}
			for(int row = 0; row < cells.length; row++) {
				for(int col = 0; col < cells[0].length; col++) {
					if(cells[row][col].filled == true) {
						break;
					}
					if(m.cells[rowStart - rowOffset + row][colStart + col].filled == false){
						return false;
					}
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}
	
	public void addTo(Maze m, int rowStart, int colStart) {
		for(int row = 0; row < cells.length; row++){
			for(int col = 0; col < cells[0].length; col++) {
				if(cells[row][col].filled == true) {
					
					m.cells[rowStart - rowOffset + row][colStart + col].filled = true;
					if(colStart + col != 0) {
						m.cells[rowStart - rowOffset + row][colStart + col].horizShiftable = cells[row][col].horizShiftable;
					}
					m.cells[rowStart - rowOffset + row][colStart + col].vertShiftable = cells[row][col].vertShiftable;

				}
				
				for(int dir = 0; dir < 4; dir++) {
					if(cells[row][col].connected[dir] == true) {
						m.cells[rowStart - rowOffset + row][colStart + col].connected[dir] = true;					}
				}
			}
		}
	}
}
