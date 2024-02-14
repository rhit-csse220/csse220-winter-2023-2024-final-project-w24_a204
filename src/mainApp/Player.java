package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Player
 * 
 * @author A204 Purpose: is the playable character
 */
public class Player {

	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLUE;
	private static final Color SHIELDED_COLOR = new Color(51, 204, 255);
	private static final int SET_LIFE = 3;
	private static final int IFRAME_DURATION = 10;
	private double xPos;
	private double yPos;
	private double yVelocity = 0;
	private static final double X_VELOCITY = 5;
	private int lives = SET_LIFE;
	private int coins = 0;
	private int iframe = 0;
	private boolean shielded = false;

	/**
	 * ensures that Player has a xPos and yPos
	 * 
	 * @param xPos the x-position of the upper left corner of Player
	 * @param yPos the y-position of the upper left corner of Player
	 */
	public Player(double xPos, double yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);

	}

	public void fly() {
		yVelocity = 9;
		yPos -= yVelocity;
	}

	public void fall() {
		yVelocity = -9;
		yPos -= yVelocity;
	}

	public void move() {
		xPos += X_VELOCITY;
	}
	
	public void iframeCountdown() {
		if(iframe > 0) {
			iframe-=5;
		}
	}

	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (this.xPos > 1150) {
			this.xPos = 1150;
		}
		Rectangle2D.Double player = new Rectangle2D.Double(getxPos(), getyPos(), EDGE_LENGTH, EDGE_LENGTH);
		if (shielded) {
			g2.setColor(SHIELDED_COLOR);
		} else {
			g2.setColor(COLOR);
		}
		g2.fill(player);

	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, EDGE_LENGTH, EDGE_LENGTH);
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public int getLives() {
		return lives;
	}

	public void loseLife() {
		if (shielded) {
			shielded = false;
		} else if (iframe <= 0) {
			this.lives--;
			this.iframe = IFRAME_DURATION;
		}
	}

	public void gainLife() {
		this.lives++;
	}

	public int getCoins() {
		return coins;
	}

	public void collectCoin() {
		this.coins++;
	}

	public void restart() {
		this.lives = SET_LIFE;
		this.coins = 0;
	}

	public boolean checkShielded() {
		return shielded;
	}

	public void shieldToggle() {
		shielded = !shielded;
	}

	public boolean nextLevel() {
		if (this.xPos > 350) {
			return true;
		}
		return false;
	}
}