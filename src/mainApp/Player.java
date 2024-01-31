package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

//hi
public class Player {
	
	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLUE;
	private int xPos;
	private int yPos;
	
	public Player(int xPos, int yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		
	}
	
	public void fly() {
		
	}
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double player = new Rectangle2D.Double(getxPos(), getyPos(), EDGE_LENGTH, EDGE_LENGTH);
		g2.setColor(COLOR);
		g2.fill(player);

	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
}
