package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Missile extends Collidable {
	private static final Color COLOR = Color.ORANGE;
	private static final int EDGE_LENGTH = 50;
//	private int delay;
	private boolean homing = false;
	private double velocity;

	public Missile(boolean homing) {
		super(1200, ((int) (450 * Math.random() + EDGE_LENGTH))/5*5);
		this.homing = homing;
		if (homing) {
			velocity = 2.5;
		} else {
			velocity = 10;
		}
//		this.delay = delay;
	}

	@Override
	public void drawOn(Graphics g) {
		// TODO Auto-generated method stub
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
		g2.setColor(COLOR);
		g2.fill(missile);
		this.setDamageTrue();
	}

	public void homesIn(double otherXPos, double otherYPos) {
		if (this.homing) {
			if (yPos > otherYPos && this.xPos > otherXPos) {
				yPos -= velocity;
			} else if (yPos < otherYPos && this.xPos > otherXPos) {
				yPos += velocity;
			}
		}
	}

//	public void delayCountdown() {
//		if (this.delay > 0) {
//			if(this.homing) {
//				this.delay -= 4;
//			}else {
//				this.delay -= 10;
//			}
//		}
//	}

	public void move() {
		if (xPos > -50) {
			xPos -= velocity;
		}
	}

//	public void setDelay(int delay) {
//		this.delay = delay;
//	}
//
//	public int getDelay() {
//		return delay;
//	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	protected double getHeight() {
		// TODO Auto-generated method stub
		return EDGE_LENGTH;
	}

	@Override
	protected double getWidth() {
		// TODO Auto-generated method stub
		return EDGE_LENGTH;
	}

	@Override
	protected void collideWith(Player p) {
		// TODO Auto-generated method stub
		p.loseLife();
	}
}
