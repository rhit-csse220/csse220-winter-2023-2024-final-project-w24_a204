package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Class: PowerUP
 * 
 * @author A204 Purpose: acts as a power up that player can pick up
 */
public class PowerUP extends Collidable {

	private static final int RADIUS = 25;
	private static final Color LIFE_COLOR = new Color(255, 102, 102);
	private static final Color SHIELD_COLOR = new Color(51, 204, 255);
	private double powerSelector = 0;

	/**
	 * ensures that PowerUP has a xPos and yPos
	 * 
	 * @param xPos the x-position of the upper left corner of PowerUP
	 * @param yPos the y-position of the upper left corner of PowerUP
	 */
	public PowerUP(double xPos, double yPos) {
		super((int) xPos, (int) yPos);
		powerSelector = Math.random();
	}

	/**
	 * ensures: drawing of the PowerUP depending which power up is rolled
	 * 
	 * @param g the graphics that the PowerUP is drawn onto
	 */
	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double powerup = new Ellipse2D.Double(xPos, yPos, 2 * RADIUS, 2 * RADIUS);
		if (powerSelector > .5) {
			g2.setColor(LIFE_COLOR);
		} else {
			g2.setColor(SHIELD_COLOR);
		}
		g2.fill(powerup);
	}

	@Override
	protected double getHeight() {
		return RADIUS * 2;
	}

	@Override
	protected double getWidth() {
		return RADIUS * 2;
	}

	/**
	 * ensures: that depending on which power is rolled, player recieves a different
	 * effect when colliding with the PowerUP
	 * 
	 * @param p the player that PowerUP collides with
	 */
	@Override
	protected void collideWith(Player p) {
		this.markToRemove();
		if (powerSelector > .5) {
			p.gainLife();
		} else if (powerSelector <= .5 && !p.checkShielded()) {
			p.shieldToggle();
		}
	}

	/**
	 * ensures: that PowerUP will not be inside a Collidable
	 * 
	 * @param c the collidable that PowerUP collides with
	 */
	protected void collideWith(Collidable c) {
		this.markToRemove();
	}

	@Override
	public String getClassName() {
		return this.getClass().getSimpleName();
	}
}
