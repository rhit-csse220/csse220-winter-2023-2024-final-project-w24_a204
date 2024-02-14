package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Missile
 * @author A204
 * Purpose: acts as a moving missile that harms the player
 */
public class Missile extends Collidable {
	private static final Color NORMAL_COLOR = Color.ORANGE;
	private static final Color HOMING_COLOR = new Color(255,152,0);
	private static final int EDGE_LENGTH = 50;
	private boolean homing = false;
	private double velocity;

	/**
	 * ensures that PowerUP has a xPos and yPos
	 * @param xPos the x-position of the upper left corner of Missile
	 * @param yPos the y-position of the upper left corner of Missile
	 * @param homing the boolean that decides if a missile is homing
	 */
	public Missile(boolean homing) {
		super(1200, ((int) (450 * Math.random() + EDGE_LENGTH))/5*5);
		this.homing = homing;
		if (homing) {
			velocity = 2.5;
		} else {
			velocity = 10;
		}
	}

	/**
	 * ensures: drawing of the Missile depending on if it is homing and not have it
	 * outside the barriers
	 * @param g the graphics that the Missile is drawn onto
	 */
	@Override
	public void drawOn(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		if (yPos < 50) {
			yPos = 50;
		} else if (yPos > 400) {
			yPos = 400;
		}
		if (xPos > 1150) {
			xPos = 1150;
		}
		Rectangle2D.Double missile = new Rectangle2D.Double(getxPos(), getyPos(), EDGE_LENGTH, EDGE_LENGTH);
		if(homing) {
			g2.setColor(HOMING_COLOR);
		}else {
			g2.setColor(NORMAL_COLOR);
		}
		g2.fill(missile);
		this.setDamageTrue();
	}

	/**
	 * ensures: homing of the 
	 * @param otherXPos the x-position of the other object
	 * @param otherYPos the y-position of the other object
	 */
	public void homesIn(double otherXPos, double otherYPos) {
		if (this.homing) {
			if (yPos > otherYPos-1 && this.xPos > otherXPos) {
				yPos -= velocity;
			} else if (yPos < otherYPos+1 && this.xPos > otherXPos) {
				yPos += velocity;
			}
		}
	}

	/**
	 * ensures: the missile moves forwards
	 */
	public void move() {
		if (xPos > -50) {
			xPos -= velocity;
		}
	}

	/**
	 * ensures: returns the x-position
	 * @return the current x-position, xPos
	 */
	public double getxPos() {
		return xPos;
	}

	/**
	 * ensures: sets the x-position
	 * @param xPos the current x-position to set this xPos to
	 */
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	/**
	 * ensures: returns the y-position
	 * @return the current y-position, yPos
	 */
	public double getyPos() {
		return yPos;
	}

	/**
	 * ensures: sets the y-position
	 * @param yPos the current y-position to set this yPos to
	 */
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	protected double getHeight() {
		return EDGE_LENGTH;
	}

	@Override
	protected double getWidth() {
		return EDGE_LENGTH;
	}

	/**
	 * ensures: that if Missile collides with player, player loses a life
	 * @param p the player that Missile collides with
	 */
	@Override
	protected void collideWith(Player p) {
		p.loseLife();
	}
}
