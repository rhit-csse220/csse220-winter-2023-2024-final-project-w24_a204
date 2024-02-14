package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Class: Obstacle
 * 
 * @author A204 Purpose: acts as a obstacle that player can't pass
 */
public class Obstacle extends Collidable {
	private static final int EDGE_LENGTH = 50;
	private static final Color NORMAL_COLOR = Color.DARK_GRAY;
	private static final Color ELECTRIC_COLOR = Color.RED;
	private boolean electric;

	/**
	 * ensures that Obstacle has a xPos and yPos
	 * 
	 * @param xPos     the x-position of the upper left corner of Obstacle
	 * @param yPos     the y-position of the upper left corner of Obstacle
	 * @param electric the boolean that determines if the obstacle is an electric
	 *                 one
	 */
	public Obstacle(int xPos, int yPos, boolean electric) {
		super(xPos, yPos);
		this.electric = electric;
	}

	/**
	 * ensures: drawing of the Barrier depending on if it electrical
	 * 
	 * @param g the graphics that the Barrier is drawn onto
	 */
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double obstacle = new Rectangle2D.Double(xPos, yPos, EDGE_LENGTH, EDGE_LENGTH);
		if (electric) {
			g2.setColor(ELECTRIC_COLOR);
			this.setDamageTrue();
		} else {
			g2.setColor(NORMAL_COLOR);
		}
		g2.fill(obstacle);
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
	 * ensures: that if Obstacle collides with the player, if it is electrical
	 * player loses a life, but if the Obstacle isnt electrical, player will press
	 * against it
	 * 
	 * @param p the player that Barrier collides with
	 */
	@Override
	protected void collideWith(Player p) {
		if (electric) {
			p.loseLife();
		} else {
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
