package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

//hi
public class Player {
	
	private static final int EDGE_LENGTH = 50;
	private static final Color COLOR = Color.BLUE;
	private static final int SET_LIFE = 3;
	private double xPos;
	private double yPos;
	private double yVelocity = 0;
	private static final double X_VELOCITY = 5;
	private int lives = SET_LIFE;
	private int coins = 0;
	
	public Player(double xPos, double yPos) {
		this.setxPos(xPos);
		this.setyPos(yPos);
		
	}
	
	public void fly() {
		yVelocity = 9;
		yPos -= yVelocity;
	}
	
	public void fall() {
		yVelocity = -9;
		yPos -= yVelocity;
	}
	
	public void move() {
		xPos += X_VELOCITY;
	}
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(yPos < 50) {
			yPos = 50;
		}else if(yPos > 400) {
			yPos = 400;
		}
		if(xPos > 1150) {
			xPos = 1150;
		}
		Rectangle2D.Double player = new Rectangle2D.Double(getxPos(), getyPos(), EDGE_LENGTH, EDGE_LENGTH);
		g2.setColor(COLOR);
		g2.fill(player);

	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void loseLife() {
		this.lives--;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public void collectCoin() {
		this.coins++;
	}
	
	public void restart() {
		this.lives = SET_LIFE;
		this.coins = 0;
	}
}
