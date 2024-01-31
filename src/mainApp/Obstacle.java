package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Obstacle extends Collidable{
	private static final int EDGE_LENGTH = 50;
	private static final Color NORMAL_COLOR = Color.DARK_GRAY;
	private static final Color ELECTRIC_COLOR = Color.RED;
	private boolean electric;
	
	public Obstacle(int xPos, int yPos, boolean electric) {
		super(xPos, yPos);
		this.electric = electric;
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double obstacle = new Rectangle2D.Double(xPos, yPos, EDGE_LENGTH, EDGE_LENGTH);
		if(electric) {
			g2.setColor(ELECTRIC_COLOR);
		}else{
			g2.setColor(NORMAL_COLOR);
		}
		g2.fill(obstacle);
	}
	
}
