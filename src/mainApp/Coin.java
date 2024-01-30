package mainApp;

import java.awt.Color;

public class Coin extends Collidable{
	private static final int RADIUS = 25;
	private static final Color COLOR = Color.YELLOW;
	
	public Coin(int xPos, int yPos) {
		super(xPos, yPos);
	}
}
