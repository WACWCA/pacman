import java.util.ArrayList;

import javax.swing.JFrame;

public class Maze {

	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;

	public static final int NUM_ROWS = 9;
	public static final int NUM_COLS = 5;
	public int[][] board;
	public Cell[][] cells;
	private PiecesBag piecesBag;
	private ArrayList<Cell> leadingCells;
	public Cluster[][] clusters;

	public Maze() {
		piecesBag = new PiecesBag();
		leadingCells = new ArrayList<>();
		cells = new Cell[NUM_ROWS][NUM_COLS];
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				cells[row][col] = new Cell(col, row);
			}
		}

		cells[3][0].filled = true;
		cells[3][0].connected[DOWN] = true;
		cells[3][0].connected[RIGHT] = true;
		cells[3][0].filled = true;

		cells[3][1].filled = true;
		cells[3][1].connected[DOWN] = true;
		cells[3][1].connected[LEFT] = true;

		cells[4][0].filled = true;
		cells[4][0].connected[UP] = true;
		cells[4][0].connected[RIGHT] = true;

		cells[4][1].filled = true;
		cells[4][1].connected[UP] = true;
		cells[4][1].connected[LEFT] = true;
		int i = 0;
		evaluateLeadingCells();
		while (leadingCells.size() != 0) {
			int rand = (new java.util.Random()).nextInt(leadingCells.size());
			Cell tempCell = leadingCells.get(rand);
			piecesBag.addRandomPieceFirstRow(this, tempCell.y, tempCell.x);
			// if(cells[tempCell.y][tempCell.x].filled = false) {
			// break;
			// }
			evaluateLeadingCells();
			i++;
		}
		chooseSpecialCells();
		mirror();
		createBoard();
	}

	private void addPiece(Piece p, int row, int col) {
		int rand = (new java.util.Random()).nextInt(NUM_ROWS);
		if (p.canBePlaced(this, row, col)) {
			p.addTo(this, row, col);
		}
	}

	private void evaluateLeadingCells() {
		leadingCells = new ArrayList<>();
		int col = 0;
		int i = 0;
		while (leadingCells.size() == 0 && col < NUM_COLS - 1) {
			for (int row = 0; row < NUM_ROWS; row++) {
				if (cells[row][col].filled == false) {
					leadingCells.add(cells[row][col]);
				}
			}
			col++;
		}
	}

	public void reload() {
		piecesBag = new PiecesBag();
		leadingCells = new ArrayList<>();
		cells = new Cell[NUM_ROWS][NUM_COLS];
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				cells[row][col] = new Cell(col, row);
			}
		}

		cells[3][0].filled = true;
		cells[3][0].connected[DOWN] = true;
		cells[3][0].connected[RIGHT] = true;
		cells[3][0].filled = true;

		cells[3][1].filled = true;
		cells[3][1].connected[DOWN] = true;
		cells[3][1].connected[LEFT] = true;

		cells[4][0].filled = true;
		cells[4][0].connected[UP] = true;
		cells[4][0].connected[RIGHT] = true;

		cells[4][1].filled = true;
		cells[4][1].connected[UP] = true;
		cells[4][1].connected[LEFT] = true;
		int i = 0;
		evaluateLeadingCells();
		while (leadingCells.size() != 0) {
			int rand = (new java.util.Random()).nextInt(leadingCells.size());
			Cell tempCell = leadingCells.get(rand);
			piecesBag.addRandomPieceFirstRow(this, tempCell.y, tempCell.x);
			// if(cells[tempCell.y][tempCell.x].filled = false) {
			// break;
			// }
			evaluateLeadingCells();
			i++;
		}
		chooseSpecialCells();
		mirror();
		createBoard();
	}

	public void mirror() {
		Cell[][] finalCells = new Cell[9][9];
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[0].length; col++) {
				finalCells[row][col + 4] = cells[row][col];
				finalCells[row][4].connected[LEFT] = finalCells[row][4].connected[RIGHT];
			}
		}
		for (int row = 0; row < finalCells.length; row++) {
			for (int col = 0; col < 4; col++) {
				finalCells[row][col] = mirrorCell(finalCells[row][finalCells[0].length - 1 - col]);
				finalCells[row][col].x = finalCells[row][col].x - 1;
			}
		}
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[0].length; col++) {
				cells[row][col].x += 4; ////////////
			}
		}
		cells = finalCells;

	}

	public static Cell mirrorCell(Cell c) {
		Cell returnCell = new Cell(c);
		returnCell.connected[LEFT] = c.connected[RIGHT];
		returnCell.connected[RIGHT] = c.connected[LEFT];

		returnCell.x = 5 - returnCell.x;

		return returnCell;
	}

	public void chooseSpecialCells() {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[0].length; col++) {
				if (cells[row][col].vertShiftable == true) {

					// cells[row][col].vertShiftable = false;

					for (int row2 = row + 1; row2 < cells.length; row2++) {
						cells[row2][col].vertShiftable = false;
					}
				}

			}
		}
		for (int row = 0; row < cells.length; row++) {
			for (int col = cells[0].length - 1; col >= 0; col--) {
				if (cells[row][col].horizShiftable == true) {
					// cells[row][col].horizShiftable = false;
					for (int col2 = col - 1; col2 >= 0; col2--) {
						cells[row][col2].horizShiftable = false;
					}
				}
			}
		}
	}

	public void createBoard() {
		clusters = new Cluster[9][9];
		for (Cell[] cs : cells) {
			for (Cell c : cs) {
				clusters[c.y][c.x] = new Cluster(c);
			}
		}
		board = new int[31][28];
		for (int row = 0; row < board.length; row++) {
			board[row][0] = 0;
			board[row][board[0].length - 1] = 0;
		}
		for (int col = 0; col < board[0].length; col++) {
			board[0][col] = 0;
			board[board.length - 1][col] = 0;
		}

		// adjust
		int i = 0;
		int j = 0;

		for (int row = 0; row < cells.length; row++) {

			for (int col = 0; col < cells[0].length; col++) {
				if (cells[row][col].vertShiftable == true) {
					i++;
					for (int row2 = row + 1; row2 < cells.length; row2++) {

						clusters[row2][col].increaseStartRow();
					}
				}
				if (cells[row][col].horizShiftable == true) {
					j++;
					for (int col2 = col + 1; col2 < cells[0].length; col2++) {

						if (col != cells[0].length - 1 - col2) {
						}
						clusters[row][col2].decreaseStartCol();

					}
				}
			}
		}

		if (i != 9 || j != 18) {
			reload();
		} else {
			for (int row = 0; row < clusters.length; row++) {
				for (int col = 0; col < clusters[0].length; col++) {
					// System.out.println(clusters[row][col].getStartRow() + " " +
					// clusters[row][col].getStartCol());
					for (int r = 0; r < clusters[row][col].cluster.length; r++) {
						for (int c = 0; c < clusters[row][col].cluster[0].length; c++) {
							int startR = clusters[row][col].getStartRow();
							int startC = clusters[row][col].getStartCol();
							// System.out.println(1 + c + startC);

							try {
								if (!(board[1 + r + startR][1 + c + startC] == 1
										&& clusters[row][col].isEmptyCorner(r, c)) && clusters[row][col].cluster[r][c] != -1) {
									board[1 + r + startR][1 + c + startC] = clusters[row][col].cluster[r][c];
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("error");
							}
						}
					}
				}
			}
		}
		
		
		
		for(int row = 1; row < board.length - 1; row++) {
			for(int col = 1; col < board[0].length - 1; col++) {
				if(board[row][col] == 1) {
					int numConnections = 0;
					if(board[row + 1][col] == 1) {
						numConnections++;
					}
					if(board[row - 1][col] == 1) {
						numConnections++;
					}
					if(board[row][col + 1] == 1) {
						numConnections++;
					}
					if(board[row][col - 1] == 1) {
						numConnections++;
					}
					if(numConnections == 1) {
						reload();
					}
				}
			}
		}
		for (int row = 13; row < 16; row++) {
			for (int col = 11; col < 17; col++) {
				board[row][col] = 3;
			}
		}
		board[12][13] = 3;
		board[12][14] = 3;
		
	}
	

}
