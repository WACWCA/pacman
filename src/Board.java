import java.awt.Color;
/**
 * Contains an array representing the game board, 
 */
import java.util.ArrayList;
public class Board {
	public static final int WALL = 0;
	public static final int SPACE = 1;
	public static final int VOID = 2;
	public static final int ENEMY_SPAWN = 3;
	public static final int PORTAL = 4;
	public static final int LEFT = 10;
	public static final int UP = 11;
	public static final int RIGHT = 12;
	public static final int DOWN = 13;
	public static final int DOT_OFFSET = 3;
	public final int CHAR_OFFSET;
	private final int originalDotSize;
	private boolean notFastMode = true;
	private final int SQUARE_LENGTH;
	private boolean characterAttackMode = false;
	private int score = 0;
	private int[][] board;
	private Round round;
	private ArrayList<EnemyFixed> enemies = new ArrayList<>();
	private ArrayList<Dot> dots = new ArrayList<>();
	private ArrayList<Portal> portals = new ArrayList<>();
	private Character character;
	private Maze maze;
	private boolean isFirstMove = true;
	
	/**
	 * Creates new Board with specified square lengths and linked to the current round
	 * @param squareLength number of pixels long each square is
	 * @param round the Round that holds this Board so it can access the Round
	 */
	public Board(int squareLength, Round round) {
		this.score = round.getScore();
		this.round = round;
		
		maze = new Maze();
		board = maze.board;
		
		SQUARE_LENGTH = round.getSquareLength();
		createPortals();
		CHAR_OFFSET = round.getCharOffset();
		character = new Character(13 * squareLength, 23 * squareLength, 4, this, Color.YELLOW);
		addEnemy(new EnemyFixed(12 * SQUARE_LENGTH, 14 * SQUARE_LENGTH, Color.RED, 3, this, 0, 0));
		addEnemy(new EnemyFixed(13 * SQUARE_LENGTH, 14 * SQUARE_LENGTH, Color.PINK, 2, this, 28 * SQUARE_LENGTH, 0));		
		addEnemy(new EnemyFixed(14 * SQUARE_LENGTH, 14 * SQUARE_LENGTH, Color.MAGENTA, 2, this, 0, 31 * SQUARE_LENGTH));	
		addEnemy(new EnemyFixed(15 * SQUARE_LENGTH, 14 * SQUARE_LENGTH, Color.ORANGE, 3, this, 28 * SQUARE_LENGTH, 31 * SQUARE_LENGTH));	
		createSpecialDots();
		for(int row = 0; row < getRows(); row++) {
			for(int col = 0; col < getCols(); col++) {
				if(board[row][col] == SPACE) {
					addDot(new Dot(col * SQUARE_LENGTH, row * SQUARE_LENGTH));
				}
			}
		}
		originalDotSize = dots.size();
		
	}
	
	/**
	 * Removes dot if there is one at the coordinate and the character is touching it
	 * @param pacmanx1 characters x coord
	 * @param pacmany1 characters y coord
	 * @param dotx1 x coord of dot to look for
	 * @param doty1 y coord of dot to look for
	 */
	public void eatPotentialDot(int pacmanx1, int pacmany1, int dotx1, int doty1) {
		if(areTwoSquaresTouching(pacmanx1, pacmany1, dotx1 + DOT_OFFSET, doty1 + DOT_OFFSET, SQUARE_LENGTH)) {
			removeDot(dotx1, doty1);
		}
		if(notFastMode && dots.size() < originalDotSize / 3) {
			notFastMode = false;
			for(EnemyFixed e: enemies) {
				e.increaseSpeed();
			}
		}
		if(dots.size() == 0) {
			round.win();
		}
	}
	
	/**
	 * Increases the speed of all enemies
	 */
	public void speedUp() {
		for(EnemyFixed e: enemies) {
			e.increaseSpeed();
		}
		character.increaseSpeed();
	}
	
	/**
	 * Determines if the specified sport is available
	 * @param x x coordinate to check for availability 
	 * @param y y coordinate to check for availability
	 * @return true if available
	 */
	public boolean isSpaceAvailable(int x, int y) {
		if(board[y / SQUARE_LENGTH][x / SQUARE_LENGTH] != SPACE) {
			return false;
		}
		if(areTwoSquaresTouching(character.getX(), character.getY(), x, y, SQUARE_LENGTH)) {
			return false;
		}
		
		for(EnemyFixed e: enemies) {
			if(areTwoSquaresTouching(e.getX(), e.getY(), x, y, SQUARE_LENGTH)) {
				return false;
			}
		}
		
		for(Dot d: dots) {
			if(areTwoSquaresTouching(d.getX(), d.getY(), x, y, SQUARE_LENGTH)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Evaluates if space is suitable for enemy
	 * @param x possible x coord
	 * @param y possible y coord
	 * @return suitability of spot
	 */
	public boolean isSpaceAvailableForEnemy(int x, int y) {
		if(board[y / SQUARE_LENGTH][x / SQUARE_LENGTH] != SPACE && 
				board[y / SQUARE_LENGTH][x / SQUARE_LENGTH] != ENEMY_SPAWN) {
			return false;
		}
		if(areTwoSquaresTouching(character.getX(), character.getY(), x, y, SQUARE_LENGTH)) {
			return false;
		}
		
		for(EnemyFixed e: enemies) {
			if(areTwoSquaresTouching(e.getX(), e.getY(), x, y, SQUARE_LENGTH)) {
				return false;
			}
		}
		
		for(Dot d: dots) {
			if(areTwoSquaresTouching(d.getX(), d.getY(), x, y, SQUARE_LENGTH)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Attempts to the given enemy to the arraylist of enemies for the board
	 * Will not accept enemy if the space it's assigned to is illegal
	 * @param e enemy to be added
	 */
	private void addEnemy(EnemyFixed e) {
		if(board[((int)e.getY()) / SQUARE_LENGTH][((int)e.getX()) / SQUARE_LENGTH] == WALL) {
			System.out.println("Tried to add enemy on a wall");
		} else if(isSpaceAvailableForEnemy((int)e.getX(), (int)e.getY())) {
				enemies.add(e);
			} else {
				System.out.println("Tried to add an enemy on an enemy");
			}
	}
	
	/**
	 * Attempts to add the given dot to the ArrayList of dots for the board
	 * Will not accept dot if the spac it's assigned to is illegal
	 * @param d the dot to be added
	 */
	private void addDot(Dot d) {
		 if(character.getX() != d.getX() || character.getY() != d.getY()) {
			 	boolean b = false;
				for(Dot dot: dots) {
					if(dot instanceof SpecialDot) {
						if(dot.getX() != d.getX() || dot.getY() != d.getY()) {
							b = false;
						}
					}
				}
				if(!b) {
					dots.add(d);
				}
			} else {
				System.out.println("Tried to add a dot on an enemy");
			}
	}
	
	/**
	 * Attempts to add the given SpecialDot to the ArrayList of dots
	 * @param d SpecialDot to potentially add
	 */
	private void addSpecialDot(SpecialDot d) {
		
		 if(board[d.getY() / SQUARE_LENGTH][d.getX() / SQUARE_LENGTH] == 1 && (character.getX() != d.getX() || character.getY() != d.getY())) {
				dots.add(d);
			} else {
				System.out.println("Tried to add a dot on an enemy");
			}
	}
	
	/**
	 * Retrieve the current score
	 * @return current score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Attempts to add portal to ArrayList of portals
	 * @param p portal to potentially add
	 */
	private void addPortal(Portal p) {
		if(board[p.getFirstY() / SQUARE_LENGTH][p.getFirstX() / SQUARE_LENGTH] == PORTAL && board[p.getSecondY() / SQUARE_LENGTH][p.getSecondX() / SQUARE_LENGTH] == PORTAL) {
			portals.add(p);		}
	}
	
	/**
	 * Attempts to remove dot that has specified coordinates
	 * @param dotx x coord of dot to remove
	 * @param doty y coord of dot to remove
	 */
	private void removeDot(int dotx, int doty) {
		for(int i = 0; i < dots.size(); i++) {
			Dot d = dots.get(i);
			if(d.getX() == dotx && d.getY() == doty) {
				if(d instanceof SpecialDot) {
					signalAttackMode();
					score += 40;
					round.updateScore();
				}
				score += 10;
				round.updateScore();
				dots.remove(d);
			}
		}
	}
	
	/**
	 * Change the character attack mode for the board and enemies/character
	 * Starts scatter mode for all enemies
	 * @param b
	 */
	public void setCharacterAttackMode(boolean b) {
		characterAttackMode = b;
		if (b) {
			for(EnemyFixed e: enemies) {
				e.startScatterMode();
			}
		} 
	}
	
	/**
	 * Character at specified coords is sent to the opposite portal end if there is a portal there
	 * @param x x coord of character
	 * @param y y coord of character
	 */
	public void teleportCharacter(int x, int y) {
		for(Portal p: portals) {
			if(x == p.getFirstX() && y == p.getFirstY()) {
				character.teleport(p.getSecondX(), p.getSecondY());
			} else if(x == p.getSecondX() && y == p.getSecondY()) {
				character.teleport(p.getFirstX(), p.getFirstY());
			}
		}
	}
	
	/**
	 * Enemy at specified coords is sent to the opposite portal end if there is a portal there
	 * @param x x coord of enemy
	 * @param y y coord of enemy
	 */
	public void teleportEnemy(int x, int y, EnemyFixed e) {
		for(Portal p: portals) {
			if(x == p.getFirstX() && y == p.getFirstY()) {
				e.teleport(p.getSecondX(), p.getSecondY());
			} else if(x == p.getSecondX() && y == p.getSecondY()) {
				e.teleport(p.getFirstX(), p.getFirstY());
			}
		}
	}
	
	/** Returns a COPY of the enemies list. */
	public ArrayList<EnemyFixed> getEnemies() {
		return new ArrayList<EnemyFixed>(enemies);
	}
	
	/** Returns a COPY of the dots list. */ 
	public ArrayList<Dot> getDots() {
		return new ArrayList<Dot>(dots);
	}
	
	/**
	 * Retrieve current character
	 * @return Character object
	 */
	public Character getCharacter() {
		return new Character(character);
	}
	
	/** Returns the specified entry of the landscape array (either WATER or ROCK). */
	public int getSquare(int row, int col) {
		return board[row][col];
	}
	
	/**
	 * Get number of rows on board
	 * @return num rows
	 * 
	 */
	public int getRows() {
		return board.length;
	}
	
	/**
	 * Get number of cols on board
	 * @return num cols
	 */
	public int getCols() {
		return board[0].length;
	}
	
	/**
	 * Change the intended direction of the character
	 * @param direction the direction to tell the character to move
	 */
	public void setCharacterDirection(int direction) {
		character.setDirection(direction);
	}
	
	/**
	 * Moves all the enemies and characters associated with the board
	 */
	public void move() {
		
		for(EnemyFixed e: enemies) {
			e.move();
		}
		character.move();
		
	}
	
	/**
	 * Retrieve the number of pixels long each square is
	 * @return Square length
	 */
	public int getSquareLength() {
		return SQUARE_LENGTH;
	}
	
	/**
	 * Have the character eat the given enemy
	 * @param e Enemy to be eaten
	 */
	public void eatEnemy(EnemyFixed e){
		score += 200;
		round.updateScore();
		e.die();
	}
	
	/**
	 * Check if a character, enemy, or dot should be eaten
	 */
	public void checkEats() {
		for(EnemyFixed e: enemies) {
			if(areTwoSquaresTouching(e.getX() + CHAR_OFFSET , e.getY() + CHAR_OFFSET, character.getX() + CHAR_OFFSET, character.getY() + CHAR_OFFSET, SQUARE_LENGTH - 2 * CHAR_OFFSET)) {
				if(!characterAttackMode) {
					round.loseLife();
				} else {
					eatEnemy(e);
				}
			}
		}
		eatPotentialDot(character.getX(), character.getY(), character.getXRounded(), character.getYRounded());

		
	}
	
	/**
	 * Start character attack mode
	 */
	public void signalAttackMode() {
		setCharacterAttackMode(true);
		for(EnemyFixed e: enemies) {
			e.setColor(Color.BLUE);
		}
		character.signalAttackMode();
	}
	
	/**
	 * Stop the character attack mode
	 */
	public void endAttackMode() {
		setCharacterAttackMode(false);
		for(EnemyFixed e: enemies) {
			e.resetColor();
		}
	}
	
	/**
	 * Put all enemies and characters at original spawn points
	 */
	public void resetPositions() {
		for(EnemyFixed e: enemies) {
			e.die();
		}
		character.respawn();
	}
	
	/**
	 * Reset everything and get a new maze for the board
	 */
	public void resetAll() {
		maze.reload();
		board = maze.board;
		createPortals();
		notFastMode = true;
		characterAttackMode = false;
		for(EnemyFixed e: enemies) {
			e.die();
			e.decreaseSpeed();
		}
		character.respawn();
		dots = new ArrayList<>();
		createSpecialDots();

		for(int row = 0; row < getRows(); row++) {
			for(int col = 0; col < getCols(); col++) {
				if(board[row][col] == SPACE) {
					addDot(new Dot(col * SQUARE_LENGTH, row * SQUARE_LENGTH));
				}
			}
		}
	}
	
	/**
	 * Determines if two squares are overlapping
	 * @param sq1x1 start x of 1st square 
	 * @param sq1y1 start y of 1st square 
	 * @param sq2x1 start x of 2nd square
	 * @param sq2y1 start y of 2nd square
	 * @param SQUARE_LENGTH length of squares
	 * @return whether the 2 squares are touchinga
	 */
	public static boolean areTwoSquaresTouching(int sq1x1, int sq1y1, int sq2x1, int sq2y1, int SQUARE_LENGTH) {
		// If sq1 starts before or they are even
		if(sq1x1 <= sq2x1) {
			// If sq1 starts before one is finished or starts at the same point
			if(sq2x1 < sq1x1 + SQUARE_LENGTH) {
				if(sq1y1 < sq2y1) {
					// if the first square ends later return false
					if(sq2y1 < sq1y1 + SQUARE_LENGTH) {
						return true;
					}
				} else {
					if(sq1y1 < sq2y1 + SQUARE_LENGTH) {
						return true;
					}
				}
			}
		} else {
			//
			if(sq1x1 < SQUARE_LENGTH + sq2x1) {
				if(sq1y1 < sq2y1) {
					// if the first square ends later return false
					if(sq2y1 < SQUARE_LENGTH + sq1y1) {
						return true;
					}
				} else {
					if(sq1y1 < SQUARE_LENGTH + sq2y1) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Add random portal at a legal location
	 */
	private void createPortals() {
		ArrayList<Integer> rowNums = new ArrayList<>();
		for(int row = 0; row < board.length; row++) {
			if(board[row][1] == 1 && board[row][2] == 1) {
				rowNums.add(row);
			}
		}
		int portal;
		 {
			int rand = (new java.util.Random()).nextInt(rowNums.size()) / 2 + 1;
			portal = rowNums.get(rand);
		}
		
		board[portal][0] = 4;
		board[portal][board[0].length - 1] = 4;
		addPortal(new Portal(0, portal * SQUARE_LENGTH, (board[0].length - 1) * SQUARE_LENGTH, portal * SQUARE_LENGTH ));
	}
	
	private void createSpecialDots() {
		int tempRow = 0;
		int tempCol = 0;
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == 1) {
					tempRow = row;
					tempCol = col;
				}
			}
		}
		addSpecialDot(new SpecialDot(tempCol * SQUARE_LENGTH, tempRow * SQUARE_LENGTH));
		addSpecialDot(new SpecialDot((board[0].length - 1 - tempCol) * SQUARE_LENGTH, tempRow * SQUARE_LENGTH));
		
		for(int row = board.length - 1; row >= 0; row--) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == 1) {
					tempRow = row;
					tempCol = col;
				}
			}
		}
		addSpecialDot(new SpecialDot(tempCol * SQUARE_LENGTH, tempRow * SQUARE_LENGTH));
		addSpecialDot(new SpecialDot((board[0].length - 1 - tempCol) * SQUARE_LENGTH, tempRow * SQUARE_LENGTH));
	}
	
	private void addVoidSpaces(){
		
	}
}
