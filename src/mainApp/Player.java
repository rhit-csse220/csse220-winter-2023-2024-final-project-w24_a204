package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

//hi
public class Player {
	
	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLUE;
	private double xPos;
	private double yPos;
	private double yVelocity = 0;
	private static final double X_VELOCITY = 5;
	
	public Player(int xPos, int yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		
	}
	
	public void fly() {
		yVelocity = 5;
		yPos -= yVelocity;
		
	}
	
	public void move() {
		xPos += X_VELOCITY;
	}
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(yPos<50) {
			yPos = 50;
		}
		if(xPos>1150) {
			xPos = 1150;
		}
		Rectangle2D.Double player = new Rectangle2D.Double(getxPos(), getyPos(), EDGE_LENGTH, EDGE_LENGTH);
		g2.setColor(COLOR);
		g2.fill(player);

	}

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
}
