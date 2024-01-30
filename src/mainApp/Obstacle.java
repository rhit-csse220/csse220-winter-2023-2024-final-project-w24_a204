package mainApp;

import java.awt.Color;

public class Obstacle extends Collidable{
	private static final int EDGE_LENGTH = 50;
	private static final Color NORMAL_COLOR = Color.DARK_GRAY;
	private static final Color ELECTRIC_COLOR = Color.RED;
	
	public Obstacle(int xPos, int yPos) {
		super(xPos, yPos);
	}
	
}
