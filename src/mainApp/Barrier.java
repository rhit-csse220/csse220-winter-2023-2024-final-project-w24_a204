package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Barrier extends Collidable {
	
	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLACK;

	public Barrier(int xPos, int yPos) {
		super(xPos, yPos);
		
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double barrier = new Rectangle2D.Double(xPos, yPos, EDGE_LENGTH, EDGE_LENGTH);
		g2.setColor(COLOR);
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
		if(p.getyPos() < 50) {
			p.setyPos(50);
		}else if(p.getyPos() > 400) {
			p.setyPos(400);
		}
	}

}
