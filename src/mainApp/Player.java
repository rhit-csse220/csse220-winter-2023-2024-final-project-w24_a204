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
	 * ensures that Player has a xPos and yPos:
	 * 
	 * @param xPos the x-position of the upper left corner of Player
	 * @param yPos the y-position of the upper left corner of Player
	 */
	public Player(double xPos, double yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);

	}

	/**
	 * ensures: player flies upwards at a constant rate
	 */
	public void fly() {
		yVelocity = 9;
		yPos -= yVelocity;
	}

	/**
	 * ensures: player falls downwards at a constant rate
	 */
	public void fall() {
		yVelocity = -9;
		yPos -= yVelocity;
	}

	/**
	 * ensures: player moves forwards at a constant rate
	 */
	public void move() {
		xPos += X_VELOCITY;
	}

	/**
	 * ensures: counts down the iframe if player has iframes
	 */
	public void iframeCountdown() {
		if (iframe > 0) {
			iframe -= 5;
		}
	}

	/**
	 * ensures: drawing of the Player depending on if it is shielded
	 * 
	 * @param g the graphics that the Player is drawn onto
	 */
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

	/**
	 * ensures: gets a bounding box
	 * 
	 * @return the bounding box
	 */
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, EDGE_LENGTH, EDGE_LENGTH);
	}

	/**
	 * ensures: returns the x-position
	 * 
	 * @return the current x-position, xPos
	 */
	public double getxPos() {
		return xPos;
	}

	/**
	 * ensures: sets the x-position
	 * 
	 * @param xPos the current x-position to set this xPos to
	 */
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	/**
	 * ensures: returns the y-position
	 * 
	 * @return the current y-position, yPos
	 */
	public double getyPos() {
		return yPos;
	}

	/**
	 * ensures: sets the y-position
	 * 
	 * @param yPos the current y-position to set this yPos to
	 */
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	/**
	 * ensures: returns the the number of lives remaining
	 * 
	 * @return the current remaining lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * ensures: sets the players' life to 0
	 */
	public void die() {
		this.lives = 0;
	}

	/**
	 * ensures: player loses a life if they aren't shielded or have been recently
	 * hit
	 */
	public void loseLife() {
		if (shielded) {
			shielded = false;
		} else if (iframe <= 0) {
			this.lives--;
			this.iframe = IFRAME_DURATION;
		}
	}

	/**
	 * ensures: player regains a life
	 */
	public void gainLife() {
		this.lives++;
	}

	/**
	 * ensures: returns the coins that player is holding
	 * 
	 * @return the current coin count
	 */
	public int getCoins() {
		return coins;
	}

	/**
	 * ensures: player gains a coin
	 */
	public void collectCoin() {
		this.coins++;
	}

	/**
	 * ensures: reset in players' lives and coins
	 */
	public void restart() {
		this.lives = SET_LIFE;
		this.coins = 0;
	}

	/**
	 * ensures: returns the shielded status of player
	 * 
	 * @return if player is shielded
	 */
	public boolean checkShielded() {
		return shielded;
	}

	/**
	 * ensures: toggles the shield on and off
	 */
	public void shieldToggle() {
		shielded = !shielded;
	}
}