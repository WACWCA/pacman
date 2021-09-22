import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* Creates a single round of the game */
public class Round extends JPanel {
	private Board board;
	private Timer timer;
	public final int SQUARE_LENGTH = 20;
	public final int CHAR_OFFSET = 3;
	private Graphics g;
	private boolean gameOver = false;
	private Color beach1 = new Color(252, 211, 3);
	private Color beach2 = new Color(3, 223, 252);
	private Color beige = new Color(245, 245, 220);
	private Color maroon = new Color(141, 2, 31);
	public final int SCREEN_DELAY = 30;
	private Game game;
	private int score;
	private boolean isFirstMove = true;
	
	
	public Round(Game game) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameOver) {
					repaint();
				}
			} };
		this.board = new Board(SQUARE_LENGTH, this);
		this.addKeyListener(new KeyHandler());
		this.game = game;
		setPreferredSize(new Dimension(560, 620));
		timer = new javax.swing.Timer(SCREEN_DELAY, al);
		timer.setInitialDelay(3000);
	}
	
	/**
	 * Draws the Board and all enemies, dots, characters
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g = g;
		
		for(int row = 0; row < board.getRows(); row++) {
			for(int col = 0; col < board.getCols(); col++ ) {
				int x = board.getSquare(row, col);
				if(x == Board.SPACE) {
					g.setColor(beige);
				} else if(x == Board.WALL) {
					g.setColor(maroon);
				} else if(x == Board.VOID) {
					g.setColor(Color.GRAY);
				} else if(x == Board.ENEMY_SPAWN) {
					g.setColor(beige);
				} else if(x == Board.PORTAL) {
					g.setColor(beige);
				}
				drawSquare(g, row, col);
			}
		}
		
		for(Dot d: board.getDots()) {			
			g.setColor(d.getColor());
			if (d instanceof SpecialDot) {
				drawSpecialDot(g, d.getX(), d.getY());
			} else {
			drawDot(g, d.getX(), d.getY()); 
			}
		}
		for(EnemyFixed e: board.getEnemies()) {
			g.setColor(e.getColor());
			drawEnemy(g, (int)e.getX(), (int)e.getY());
		}
		drawCharacter(g, board.getCharacter().getX(), board.getCharacter().getY());
		score = board.getScore();
		board.move();
		board.checkEats();
		timer.start();
	}
	
	/**
	 * Draws a single Board square
	 * @param g the graphics variable transferred from the paintComponent method
	 * @param row the row of the board to start on
	 * @param col the col of the board to start on
	 */
	private void drawSquare(Graphics g, int row, int col) {
		g.fillRect(col * SQUARE_LENGTH, row * SQUARE_LENGTH, SQUARE_LENGTH, SQUARE_LENGTH);
	}
	
	/**
	 * Draws a single dot on the board
	 * @param g the graphics variable transferred from the paintComponent method
	 * @param x the x coordinate to start drawing
	 * @param y the y coordinate to start drawing
	 */
	private void drawDot(Graphics g, int x, int y) {
	
		g.fillOval(x + 3 * SQUARE_LENGTH/8, y+ 3 * SQUARE_LENGTH/8, SQUARE_LENGTH/4, SQUARE_LENGTH/4);
	}
	
	/**
	 * Draws a single SpecialDot on the board
	 * @param g the graphics variable transferred from the paintComponent method
	 * @param x the x coordinate to start drawing
	 * @param y the y coordinate to start drawing
	 */
	private void drawSpecialDot(Graphics g, int x, int y) {
	
		g.fillOval(x +  SQUARE_LENGTH/4, y + SQUARE_LENGTH/4, SQUARE_LENGTH/2, SQUARE_LENGTH/2);
	}
	
	/**
	 * Draws a single enemy on the board
	 * @param g the graphics variable transferred from the paintComponent method
	 * @param x the x coordinate to start drawing
	 * @param y the y coordinate to start drawing
	 */
	private void drawEnemy(Graphics g, int x, int y) {
		g.fillRect(x + CHAR_OFFSET, y + CHAR_OFFSET, SQUARE_LENGTH - 2 * CHAR_OFFSET, SQUARE_LENGTH - 2 * CHAR_OFFSET);
	}
	
	/**
	 * Draws character on the board
	 * @param g the graphics variable transferred from the paintComponent method
	 * @param x the x coordinate to start drawing
	 * @param y the y coordinate to start drawing
	 */
	private void drawCharacter(Graphics g, int x, int y) {
		g.setColor(Color.YELLOW);
		g.setColor(maroon);
		g.fillRect(x + CHAR_OFFSET, y + CHAR_OFFSET, SQUARE_LENGTH - 2 * CHAR_OFFSET, SQUARE_LENGTH - 2 * CHAR_OFFSET);
	}
	
	/**
	 * Deal with a win, when the character has eaten all the dots
	 */
	public void win() {
		for(int row = 0; row < board.getRows(); row++) {
			for(int col = 0; col < board.getCols(); col++ ) {
				//gameOver = true;
				g.setColor(Color.green);
				drawSquare(g, row, col);
			}
		}
		game.signalWin();
	}
	
	/**
	 * Resets everything for the board
	 */
	public void resetAll() {
		board.resetAll();
	}
	
	/**
	 * Gets the current score
	 * @return current score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Gets the current score
	 * @return current score
	 */
	public int getSquareLength() {
		return SQUARE_LENGTH;
	}
	
	/**
	 * Gets the current score
	 * @return current score
	 */
	public int getCharOffset() {
		return CHAR_OFFSET;
	}
	
	/**
	 * updates the score for the game so it stays synced
	 */
	public void updateScore() {
		game.updateScore();
	}
	
	/**
	 * Gets the number of miliseconds between each movement
	 * @return current score
	 */
	public int getScreenDelay() {
		return SCREEN_DELAY;
	}
	
	/**
	 * Gets the current score
	 * @return current score
	 */
	public void lose() {
		gameOver = true;
		/* for(int row = 0; row < board.getRows(); row++) {
			for(int col = 0; col < board.getCols(); col++ ) {
				g.setColor(Color.RED);
				drawSquare(g, row, col);
			}
		} */
		game.signalGameEnd();
	}
	
	/**
	 * Speeds up the enemies and character
	 * @return current score
	 */
	public void speedUp() {
		board.speedUp();
	}
	
	/**
	 * Removes a character life
	 */
	public void loseLife() {
		game.loseLife();
	}
	
	/**
	 * Reset the positions of enemies and characters
	 */
	public void resetPositions() {
		board.resetPositions();
	}
	/**
	 * Changes the character direction
	 * @author Will
	 * @version 1.0
	 */
	private class KeyHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {	
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int x = e.getKeyCode();		
			if (x == 37 || x == 65) {
				board.setCharacterDirection(Board.LEFT);
			} else if (x == 38 || x == 87) {
				board.setCharacterDirection(Board.UP);
			} else if (x == 39 || x == 68) {
				board.setCharacterDirection(Board.RIGHT);
			} else if (x == 40 || x == 83) {
				board.setCharacterDirection(Board.DOWN);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}	
	}
}
