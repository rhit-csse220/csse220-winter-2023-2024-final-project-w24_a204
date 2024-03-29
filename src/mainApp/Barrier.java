package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Barrier Superclass: Collidable
 * 
 * @author A204 Purpose: Creates the barriers that are at the top and bottom of
 *         each level, confining the player. Also creates secret barriers that
 *         lead to the secret level. For example:
 * 
 *         Barrier barrier = new Barrier(50, 50, false);
 * 
 */
public class Barrier extends Collidable {

	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLACK;
	private static final Color SECRET_COLOR = new Color(21, 21, 21);
	private boolean secret;

	/**
	 * ensures: intializes the xPos, yPos, and if it is a secret non-colliding
	 * barrier
	 * 
	 * @param xPos   the x-position of the upper left corner of Barrier
	 * @param yPos   the y-position of the upper left corner of Barrier
	 * @param secret if the barrier is a secret barrier that the player can pass
	 *               through
	 */
	public Barrier(int xPos, int yPos, boolean secret) {
		super(xPos, yPos);
		this.secret = secret;
	}

	/**
	 * ensures: drawing of the Barrier depending on if it secret
	 * 
	 * @param g the graphics that the Barrier is drawn onto
	 */
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double barrier = new Rectangle2D.Double(xPos, yPos, EDGE_LENGTH, EDGE_LENGTH);
		if (secret) {
			g2.setColor(SECRET_COLOR);
		} else {
			g2.setColor(COLOR);
		}
		g2.fill(barrier);

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
	 * ensures: that if Barrier is not a secret player cant pass it
	 * 
	 * @param p the player that Barrier collides with
	 */
	@Override
	protected void collideWith(Player p) {
		if (!secret) {
			if (p.getxPos() + 45 != this.xPos) {
				if (p.getyPos() < this.yPos && p.getyPos() > this.yPos - 50) {
					p.setyPos(this.yPos - 50);
				} else if (p.getyPos() < this.yPos + 50 && p.getyPos() > this.yPos) {
					p.setyPos(this.yPos + 50);
				}
			} else {
				p.setxPos(this.xPos - 50);
			}
		}
	}

}
