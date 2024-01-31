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

}
