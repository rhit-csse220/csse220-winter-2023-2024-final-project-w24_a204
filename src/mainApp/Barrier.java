package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Barrier extends Collidable {

	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLACK;
	private static final Color SECRET_COLOR = new Color(21, 21, 21);
	private boolean secret;

	public Barrier(int xPos, int yPos, boolean secret) {
		super(xPos, yPos);
		this.secret = secret;
	}

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
		if (secret) {

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
