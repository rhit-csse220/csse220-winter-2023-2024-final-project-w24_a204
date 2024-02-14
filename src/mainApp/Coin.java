package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Class: Coin
 * Superclass: Collidable
 * @author A204
 * Purpose: Creates a coin that can be picked up by the player.
 * For example: 
 * 
 * Coin coin = new Coin(50, 50);
 * 
 */
public class Coin extends Collidable{
	private static final int RADIUS = 25;
	private static final Color COLOR = Color.YELLOW;
	
	public Coin(int xPos, int yPos) {
		super(xPos, yPos);
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double coin = new Ellipse2D.Double(xPos, yPos, 2*RADIUS, 2*RADIUS);
		g2.setColor(COLOR);
		g2.fill(coin);
	}

	@Override
	protected double getHeight() {
		// TODO Auto-generated method stub
		return RADIUS*2;
	}

	@Override
	protected double getWidth() {
		// TODO Auto-generated method stub
		return RADIUS*2;
	}

	@Override
	protected void collideWith(Player p) {
		// TODO Auto-generated method stub
		this.markToRemove();
		p.collectCoin();
		//System.out.println(p.getCoins());
	}
}
