import java.awt.Color;

/**
 * Represents the user controlled player in the game
 * @author Will
 * @version 1.0
 */
public class Character {
	private int x;
	private int y;
	private final int originalX;
	private final int originalY;
	int xRounded;
	int yRounded;
	private int direction;
	private int directionMoving;
	private int speed;
	private int originalSpeed;
	private int squareLength;
	private int frameCounter;
	private int MAX_FRAMES;
	private boolean attackMode = false;
	private Board board;
	private Color color;
	/**
	 * Creates new character at specified coordinates
	 * @param x
	 * @param y
	 * @param speed
	 * @param board
	 */
	public Character(int x, int y, int speed, Board board, Color color) {
		this.MAX_FRAMES = 200;
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.direction = Board.LEFT;
		this.directionMoving = this.direction;
		this.speed = speed;
		this.originalSpeed = this.speed;
		this.board = board;
		this.squareLength = board.getSquareLength();
		this.color = color;
	}

	public Character(Character character) {
		this(character.x, character.y, character.speed, character.board, character.color);		
		this.direction = character.direction;
	}

	
	/**
	 * Moves the character once. Attempts to move in the direction specified by 
	 * the direction variable. If this direction is illegal, it attempts to move 
	 * 
	 */
	public void move() {
		frameCounter++;
		if(frameCounter >= MAX_FRAMES) {
			board.endAttackMode();
			attackMode = false;
		} 
		int tempDirection;
		if(directionMoving != direction && isLegalMove(direction)) {
			tempDirection = direction;
			directionMoving = direction;
		} else {
			tempDirection = directionMoving;
		}
		int xIncrement = 0;
		int yIncrement = 0;
		if(tempDirection == Board.UP) {
			xIncrement = 0;
			yIncrement = -speed;
		} else if(tempDirection == Board.DOWN) {
			xIncrement = 0;
			yIncrement = speed;
		} else if(tempDirection == Board.LEFT) {
			xIncrement = -speed;
			yIncrement = 0;
		} else if(tempDirection == Board.RIGHT) {
			xIncrement = speed;
			yIncrement = 0;
		}
		int newX = x + xIncrement;
		int newY = y + yIncrement;
		xRounded = newX;
		yRounded = newY;
		if(xIncrement > 0) {
			xRounded = squareLength * ((newX) / squareLength + 1);
			yRounded = squareLength * ((y) / squareLength);
			newY = yRounded;
		} else if(xIncrement < 0) {
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
		//if(board.getSquare(y / squareLength, x / squareLength) == PORTAL) {
			
		//}
		int temp = -1;
		if(newX != xRounded || newY != yRounded) {
			temp = board.getSquare(yRounded / squareLength, xRounded / squareLength);
			if((temp == Board.WALL || temp == Board.ENEMY_SPAWN) && Board.areTwoSquaresTouching(x + xIncrement, y + yIncrement, xRounded, yRounded, squareLength)) {				
					newX = squareLength * (newX / squareLength + (newX % squareLength >= squareLength / 2? 1 : 0));
					newY = squareLength * (newY / squareLength + (newY % squareLength >= squareLength / 2? 1 : 0));
			} 
		}
		if(temp == Board.PORTAL && (Math.abs(xRounded - newX) <= speed)) {
			board.teleportCharacter(squareLength * (xRounded / squareLength), squareLength * (yRounded) / squareLength);
		} else {
			x = newX;
			y = newY;
		}
	}
	

	
	/**
	 * Evaluates the legality of the move
	 * @param direction to evaluate
	 * @return the legality of a move in the intended direction
	 */
	private boolean isLegalMove(int direction) {
		final int MAX_ERROR = 5;
		
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
		int newX = x + xIncrement;
		int newY = y + yIncrement;
		int xRounded = newX;
		int yRounded = newY;
		if(xIncrement > 0) {
			xRounded = squareLength * ((newX) / squareLength + 1);
			if(!(Math.abs(y % squareLength) <= MAX_ERROR)) {
				//System.out.println("Code 1");
				return false;
			}
			yRounded = squareLength * ((y) / squareLength);

		} else if(xIncrement < 0) {
			xRounded = squareLength * ((newX) / squareLength);
			if(!(Math.abs(y % squareLength) <= MAX_ERROR)) {
				//System.out.println("Code 1");
				return false;
			}
			yRounded = squareLength * ((y) / squareLength);
		} else if (yIncrement > 0) {
			yRounded = squareLength * ((newY) / squareLength + 1);
			if(!(Math.abs(x % squareLength) <= MAX_ERROR)) {
				//System.out.println("Code 1");
				return false;
			}
			xRounded = squareLength * ((x) / squareLength);
		} else if (yIncrement < 0) {
			yRounded = squareLength * ((newY) / squareLength);
			if(!(Math.abs(x % squareLength) <= MAX_ERROR)) {
				//System.out.println("Code 1");
				return false;
			}
			xRounded = squareLength * ((x) / squareLength);
		}
		if(newX != xRounded || newY != yRounded) {
			int temp = board.getSquare(yRounded / squareLength, xRounded / squareLength);
			if((temp == Board.WALL || temp == Board.ENEMY_SPAWN) && Board.areTwoSquaresTouching(x + xIncrement, y + yIncrement, xRounded, yRounded, squareLength)) {				
				//System.out.println("Code 5");
				return false;
			} 
		}
		return true;
	}	
	
	
	/**
	 * Moves character into resulting portal location after it enters one
	 * @param goToX x value of resulting portal the character exits
	 * @param goToY y value of the resulting portal the character exits
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
	 * Change the speed of the character
	 * @param increment amount to change speed by
	 */
	public void increaseSpeed() {
		speed++;
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
	 * @param direction direction to try to move character
	 */
	public void setDirection(int direction) {
		if(direction == Board.LEFT || direction == Board.RIGHT || direction == Board.UP || direction == Board.DOWN )
			this.direction = direction;
	}

	/**
	 * 
	 * @return current direction moving
	 */
	public int getDirectionMoving() {
		return directionMoving;
	}
	
	/**
	 * Tells this character the attack mode is over
	 */
	public void signalAttackMode() {
		frameCounter = 0;
		attackMode = true;
	}
	
	/**
	 * Retrieve current xRounded value. Coordinates of square it's heading to/ half in
	 * @return current xRounded value
	 */
	public int getXRounded() {
		return xRounded;
	}
	
	/**
	 * Retrieve current yRounded value. Coordinates of square it's heading to/ half in
	 * @return current yRounded value
	 */
	public int getYRounded() {
		return yRounded;
	}
	
	/**
	 * Move character back to spawn coordinates
	 */
	public void respawn() {
		x = originalX;
		y = originalY;
	}
}
