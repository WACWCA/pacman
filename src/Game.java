import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * Holds Round and Board paired with extra display
 * @author Will
 * @version 1.0
 */
public class Game {
	private Round round;
	private JPanel boardContainer;
	private JFrame frame;
	private boolean gameOver;
	private int score;
	private InfoPanel infoPanel;
	private int roundNumber = 1;
	private int numLives = 3;

	public Game() {
		frame = new JFrame();
		score = 0;
		gameOver = false;
		round = new Round(this);
		round.setFocusable(true);
		round.requestFocusInWindow();
		round.setAlignmentY(Component.CENTER_ALIGNMENT);
		boardContainer = new JPanel();
		boardContainer.setLayout(new BoxLayout(boardContainer, BoxLayout.Y_AXIS));
		infoPanel = new InfoPanel();
		infoPanel.setMaximumSize(new Dimension(200, 50));
		round.setMaximumSize(round.getPreferredSize());
		round.setAlignmentY(Component.CENTER_ALIGNMENT);
		boardContainer.add(infoPanel);
		boardContainer.add(round);
		frame.add(boardContainer);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		round.repaint();

	}
	
	/**
	 * Deal with the end of game
	 */
	public void signalGameEnd() {
		gameOver = true;
	}
	
	/**
	 * Update the current score of the game
	 */
	public void updateScore() {
		infoPanel.updatePanel(score + round.getScore());
	}
	
	/**
	 * Retrieve the current score
	 * @return current score value
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Deal at Game level with win
	 */
	public void signalWin() {
		round.resetAll();
		if(roundNumber == 1) {
			round.speedUp();
		} else if(roundNumber == 3) {
			round.speedUp();
		} else if(roundNumber == 5) {
			round.speedUp();
		}
		else if(roundNumber == 8) {
			round.speedUp();
		}
		roundNumber++;
	}
	
	/**
	 * Displays score and potential future information
	 * @author Will
	 * @version 1.0
	 */
	private class InfoPanel extends JPanel{
		private JLabel scoreLabel;
		public InfoPanel() {
			scoreLabel = new JLabel("" + (0));
			scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 32));
			scoreLabel.setForeground(Color.black);
			add(scoreLabel);
			setPreferredSize(new Dimension(200,30));
		}
		
		public void updatePanel(int score) {
			scoreLabel.setText("" + score);
		}
	}
	
	/**
	 * Decrease number of lives remaining and deal with character death
	 */
	public void loseLife(){
		numLives--;
		if(numLives == 0) {
			round.lose();
		}
		round.resetPositions();
	}
}
