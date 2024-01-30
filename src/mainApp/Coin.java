package mainApp;

import java.awt.Color;
import java.awt.Graphics;

public class Coin extends Collidable{
	private static final int RADIUS = 25;
	private static final Color COLOR = Color.YELLOW;
	
	public Coin(int xPos, int yPos) {
		super(xPos, yPos);
	}

	@Override
	public void drawOn(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
