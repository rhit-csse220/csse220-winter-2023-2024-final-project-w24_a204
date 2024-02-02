package mainApp;

import java.awt.Graphics;

public abstract class Collidable {
	
	protected double xPos;
	protected double yPos;
	
	public Collidable(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract void drawOn(Graphics g);
	
}
