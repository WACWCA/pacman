import java.awt.Color;
/**
 * 
 * @author Will Callan
 * @version 1.0
 * Standard enemy class
 */
public class EnemyFixed {

	private int x;
	private int y;
	private int numFramesMoved = 0;
	private final int originalX;
	private final int originalY;
	private int xRounded;
	private int yRounded;
	private int direction;
	private int directionMoving;
	private int directionPrevious;
	private int speed;
	private int baseSpeed;
	private int squareLength;
	private int MAX_FRAMES;
	private boolean attackMode = false;
	private Color color;
	private Color startColor;
	private Board board;
	private boolean hasLeftSpawn = false;
	private int scatterXCoord;
	private int scatterYCoord;
	private int scatterFrameCount;
	private boolean scatterMode;
	private static final int MAX_SCATTER_FRAMES = 200;

	/** 
	 * 
	 * @param x integer representing the current x component of the enemy
	 * @param y interger representing the current y component of the eemy
	 * @param Color representing the current color this enemy should displayed as
	 * @param speed integer representing 
	 * @param board the Board variable shared by all enemies and characters
	 * @param scatterXCoord integer representing the x coord this enemy will track during scatter mode
	 * @param scatterYCoord  integer representing the x coord this enemy will track during scatter mode
	 */
	public EnemyFixed(int x, int y, Color color, int speed, Board board, int scatterXCoord, int scatterYCoord) {
		this.MAX_FRAMES = 200;
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.color = color;
		this.startColor = color;
		this.direction = Board.UP;
		this.directionMoving = this.direction;
		this.directionPrevious = -1;
		this.speed = speed;
		this.baseSpeed = this.speed;
		this.board = board;
		this.squareLength = board.getSquareLength();
		this.scatterXCoord = scatterXCoord;
		this.scatterYCoord = scatterYCoord;
		this.scatterFrameCount = 0;
		this.scatterMode = false;
		
	}

	/**
	 * Copy constructor
	 * @param enemyFixed EnemyFixed to be copied
	 */
	public EnemyFixed(EnemyFixed enemyFixed) {
		this(enemyFixed.x, enemyFixed.y, enemyFixed.color, enemyFixed.speed, enemyFixed.board, enemyFixed.scatterXCoord, enemyFixed.scatterYCoord);
		this.direction = enemyFixed.direction;
	}


	/**
	 * Moves this enemy while it is in the enemySpawnBox
	 * Sometimes changes the directions
	 */
	private void moveInSpawn() {
		int tempDirection;
		if (directionMoving != direction && isLegalMove(direction)) {

			tempDirection = direction;
			directionPrevious = directionMoving;
			directionMoving = direction;
			rotate();

		} else {
			tempDirection = directionMoving;
		}
		int xIncrement = 0;
		int yIncrement = 0;
		if (tempDirection == Board.UP) {
			xIncrement = 0;
			yIncrement = -speed;
		} else if (tempDirection == Board.DOWN) {
			xIncrement = 0;
			yIncrement = speed;
		} else if (tempDirection == Board.LEFT) {
			xIncrement = -speed;
			yIncrement = 0;
		} else if (tempDirection == Board.RIGHT) {
			xIncrement = speed;
			yIncrement = 0;
		}
		int newX = x + xIncrement;
		int newY = y + yIncrement;
		xRounded = newX;
		yRounded = newY;
		if (xIncrement > 0) {
			xRounded = squareLength * ((newX) / squareLength + 1);
			yRounded = squareLength * ((y) / squareLength);
			newY = yRounded;
		} else if (xIncrement < 0) {
			xRounded = squareLength * ((newX) / squareLength);
			yRounded = squareLength * ((y) / squareLength);
			newY = yRounded;
		} else if (yIncrement > 0) {
			yRounded = squareLength * ((newY) / squareLength + 1);
			xRounded = squareLength * ((x) / squareLength);
			newX = xRounded;

		} else if (yIncrement < 0) {
			yRounded = squareLength * ((newY) / squareLength);
			xRounded = squareLength * ((x) / squareLength);
			newX = xRounded;
		}
		// if(board.getSquare(y / squareLength, x / squareLength) == PORTAL) {

		// }
		int temp = -1;
		temp = board.getSquare(yRounded / squareLength, xRounded / squareLength);
		if(temp == Board.SPACE) {
			hasLeftSpawn = true;
			startScatterMode();
			directionPrevious = directionMoving;
			directionMoving = Board.UP;

		}
		if ((newX != xRounded || newY != yRounded)
				&& ((temp == Board.WALL || (temp == Board.ENEMY_SPAWN && hasLeftSpawn == true)) && Board
						.areTwoSquaresTouching(x + xIncrement, y + yIncrement, xRounded, yRounded, squareLength))) {
			newX = squareLength * (newX / squareLength + (newX % squareLength >= squareLength / 2 ? 1 : 0));
			newY = squareLength * (newY / squareLength + (newY % squareLength >= squareLength / 2 ? 1 : 0));

		}
		if(newX == x && newY == y) {
			rotate();
			move();
		}
		
		if (temp == Board.PORTAL && (Math.abs(xRounded - newX) <= speed)) {
				board.teleportEnemy(squareLength * (xRounded / squareLength),
						squareLength * (yRounded) / squareLength, this);
			} else {
				x = newX;
				y = newY;
			}
		
		
	}
	
	/**
	 * Moves this enemy. Calls MoveInSpawn instead if enemy is in spawn box
	 * Sometimes changes the directions
	 */
	public void move() {
		if(!hasLeftSpawn) {
			moveInSpawn();
		} else {
			trackSquare();

			int counter = 0;
			for(int dir = Board.LEFT; dir <= Board.DOWN; dir++) {
				if(dir - 10 != (directionMoving - 10 + 2) % 4 && isLegalMove(dir)) {
					counter++;
				}
			}	
			
			
			int tempDirection = directionMoving;
			//System.out.println(counter);
			int xIncrement = 0;
			int yIncrement = 0;
			if (tempDirection == Board.UP) {
				xIncrement = 0;
				yIncrement = -speed;
			} else if (tempDirection == Board.DOWN) {
				xIncrement = 0;
				yIncrement = speed;
			} else if (tempDirection == Board.LEFT) {
				xIncrement = -speed;
				yIncrement = 0;
			} else if (tempDirection == Board.RIGHT) {
				xIncrement = speed;
				yIncrement = 0;
			}
			int newX = x + xIncrement;
			int newY = y + yIncrement;
			xRounded = newX;
			yRounded = newY;
			if (xIncrement > 0) {
				xRounded = squareLength * ((newX) / squareLength + 1);
				yRounded = squareLength * ((y) / squareLength);
				newY = yRounded;
			} else if (xIncrement < 0) {
				xRounded = squareLength * ((newX) / squareLength);
				yRounded = squareLength * ((y) / squareLength);
				newY = yRounded;
			} else if (yIncrement > 0) {
				yRounded = squareLength * ((newY) / squareLength + 1);
				xRounded = squareLength * ((x) / squareLength);
				newX = xRounded;
			} else if (yIncrement < 0) {
				yRounded = squareLength * ((newY) / squareLength);
				xRounded = squareLength * ((x) / squareLength);
				newX = xRounded;
			}
			// if(board.getSquare(y / squareLength, x / squareLength) == PORTAL) {
	
			// }
			int temp = -1;
			temp = board.getSquare(yRounded / squareLength, xRounded / squareLength);
			if(temp == Board.SPACE) {
				hasLeftSpawn = true;
			}
			if ((newX != xRounded || newY != yRounded)
					&& ((temp == Board.WALL || (temp == Board.ENEMY_SPAWN && hasLeftSpawn == true)) && Board
							.areTwoSquaresTouching(x + xIncrement, y + yIncrement, xRounded, yRounded, squareLength))) {
				newX = squareLength * (newX / squareLength + (newX % squareLength >= squareLength / 2 ? 1 : 0));
				newY = squareLength * (newY / squareLength + (newY % squareLength >= squareLength / 2 ? 1 : 0));
				
	
			}
			if (temp == Board.PORTAL) {
				board.teleportEnemy(squareLength * (xRounded / squareLength),
						squareLength * (yRounded) / squareLength, this);
			} else {
				x = newX;
				y = newY;
				
			}
		}
	}

	/** 
	 * Moves this enemy to the correctc portal exit after entering a portal
	 * @param goToX the x coordinate of the resulting portal location
	 * @param goToY the y coordinate of the resulting portal location
	 */
	public void teleport(int goToX, int goToY) {
		x = goToX;
		y = goToY;
		direction = directionMoving;
		int xIncrement = 0;
		int yIncrement = 0;
		if(direction == Board.UP) {
			xIncrement = 0;
			yIncrement = -speed;
		} else if(direction == Board.DOWN) {
			xIncrement = 0;
			yIncrement = speed;
		} else if(direction == Board.LEFT) {
			xIncrement = -speed;
			yIncrement = 0;
		} else if(direction == Board.RIGHT) {
			xIncrement = speed;
			yIncrement = 0;
		}
		x = x + xIncrement;
		y = y + yIncrement;
	}

	/**
	 * 
	 * @param direction the direction check for legality
	 * @return the plausablity of the move
	 */
	private boolean isLegalMove(int direction) {
		final int MAX_ERROR=  1;

		int xIncrement = 0;
		int yIncrement = 0;
		if (direction == Board.UP) {
			xIncrement = 0;
			yIncrement = -speed;
		} else if (direction == Board.DOWN) {
			xIncrement = 0;
			yIncrement = speed;
		} else if (direction == Board.LEFT) {
			xIncrement = -speed;
			yIncrement = 0;
		} else if (direction == Board.RIGHT) {
			xIncrement = speed;
			yIncrement = 0;
		}
		int newX = x + xIncrement;
		int newY = y + yIncrement;
		int xRounded = newX;
		int yRounded = newY;
		if (xIncrement > 0) {
			xRounded = squareLength * ((newX) / squareLength + 1);
			if (!(Math.abs(y % squareLength) <= MAX_ERROR)) {
				// System.out.println("Code 1");
				return false;
			}
			//yRounded = squareLength * ((y) / squareLength);

		} else if (xIncrement < 0) {
			xRounded = squareLength * ((newX) / squareLength);
			if (!(Math.abs(y % squareLength) <= MAX_ERROR)) {
				// System.out.println("Code 1");
				return false;
			}
			//yRounded = squareLength * ((y) / squareLength);
		} else if (yIncrement > 0) {
			yRounded = squareLength * ((newY) / squareLength + 1);
			if (!(Math.abs(x % squareLength) <= MAX_ERROR)) {
				// System.out.println("Code 1");
				return false;
			}
			//xRounded = squareLength * ((x) / squareLength);
		} else if (yIncrement < 0) {
			yRounded = squareLength * ((newY) / squareLength);
			if (!(Math.abs(x % squareLength) <= MAX_ERROR)) {
				// System.out.println("Code 1");
				return false;
			}
			//xRounded = squareLength * ((x) / squareLength);
		}
		if (newX != xRounded || newY != yRounded) {
			int temp = -1;
			try{
				temp = board.getSquare(yRounded / squareLength, xRounded / squareLength);
				} catch (ArrayIndexOutOfBoundsException e) {
					if(yRounded / squareLength < board.getRows() && yRounded / squareLength >= 0 & 
							(xRounded == board.getCols() || xRounded == -1)){
						temp = Board.PORTAL;
					}
				}
			if ((temp == Board.WALL || (temp == Board.ENEMY_SPAWN && hasLeftSpawn == true))
					&& Board.areTwoSquaresTouching(x + xIncrement, y + yIncrement, xRounded, yRounded, squareLength)) {
				// System.out.println("Code 5");
				return false;
			}
		}
		return true;
	}


	
	/**
	 * Changes directionMoving to that leading closest to the enemy
	 */
	private void trackSquare() {
		int xTrack;
		int yTrack;
		if(scatterMode) {
			if(scatterMode) {
				scatterFrameCount++;
			}
			xTrack = scatterXCoord;
			yTrack = scatterYCoord;
			if(scatterFrameCount == MAX_SCATTER_FRAMES) {
				scatterFrameCount = 0;
				endScatterMode();
			}
		} else {
			xTrack = board.getCharacter().getX();
			yTrack = board.getCharacter().getY();
		}
		numFramesMoved++;
		int counter = 0;
		int bestDirection = -1;
		double shortestDistance = -1;
		for(int dir = Board.LEFT; dir <= Board.DOWN; dir++) {
			if(isLegalMove(dir) && dir - 10 != (directionMoving - 10 + 2) % 4) {
				counter++;
				int xIncrement = 0;
				int yIncrement = 0;
				if(dir == Board.UP) {
					xIncrement = 0;
					yIncrement = -speed;
				} else if(dir == Board.DOWN) {
					xIncrement = 0;
					yIncrement = speed;
				} else if(dir == Board.LEFT) {
					xIncrement = -speed;
					yIncrement = 0;
				} else if(dir == Board.RIGHT) {
					xIncrement = speed;
					yIncrement = 0;
				}
				int newX = x + xIncrement;
				int newY = y + yIncrement;
				
				double tempDistance =  Math.pow(xTrack - newX, 2) +
						Math.pow(yTrack - newY, 2);
				if(tempDistance < shortestDistance  || bestDirection == -1) {
					bestDirection = dir;
					shortestDistance = tempDistance;
				}
			}
		}
		if(directionMoving != bestDirection && bestDirection != -1 && (numFramesMoved >( bestDirection != ((directionPrevious - 10 + 2) % 4 + 10)? 2 : 2))) {
			numFramesMoved = 0;
			directionPrevious = directionMoving;
			directionMoving = bestDirection;
		} 		
	}
	
	/**
	 * 
	 * @return current x value
	 */
	public int getX() {
		return x;
	}

	/** 
	 * 
	 * @return current y value
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return the direction this enemy is currentlyMoving
	 */
	private int getDirectionMoving() {
		return directionMoving;
	}
	
	/**
	 * 
	 */
	public void increaseSpeed() {
		speed++;
	}
	
	/**
	 * 
	 */
	public void decreaseSpeed() {
		speed--
		;
	}

	/**
	 * Kills this enemy, making it respawn. Used at new level start and character/enemy death
	 */
	public void die() {
		x = originalX;
		y = originalY;	
		hasLeftSpawn = false;
	}

	/**
	 * 
	 * @param c color to change this enemy to
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Set color to original color
	 */
	public void resetColor() {
		color = startColor;
	}

	/**
	 * 
	 * @return current enemy color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * changes enemy direction randomly (cant go backwards)
	 */
	private void rotate() {

		int rand = (new java.util.Random()).nextInt(2);
		if (directionMoving == Board.LEFT || directionMoving == Board.RIGHT) {
			if (rand == 0) {
				direction = Board.UP;
			} else if (rand == 1) {
				direction = Board.DOWN;
			} else {
				direction = directionMoving;
			}
		} else {
			if (rand == 0) {
				direction = Board.RIGHT;
			} else if (rand == 1) {
				direction = Board.LEFT;
			} else {
				direction = directionMoving;
			}
		}
	}

	/**
	 * Changes enemy movement to scatter mode. Runs for certain amount of frames
	 */
	public void startScatterMode() {
		scatterMode = true;
		scatterFrameCount = 0;
		directionPrevious = directionMoving;
		directionMoving = (directionMoving - 10 + 2) % 4 + 10;
		direction = directionMoving;
	}
	
	/**
	 * Called when scatter mode is over
	 */
	private void endScatterMode() {
		scatterMode = false;
	}
}
