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
			this.setDamageTrue();
		}else{
			g2.setColor(NORMAL_COLOR);
		}
		g2.fill(obstacle);
	}

	@Override
	protected double getHeight() {
		// TODO Auto-generated method stub
		return EDGE_LENGTH;
	}

	@Override
	protected double getWidth() {
		// TODO Auto-generated method stub
		return EDGE_LENGTH;
	}

	@Override
	protected void collideWith(Player p) {
		// TODO Auto-generated method stub
		if(electric) {
			p.loseLife();
		}else {
			if(p.getxPos() + 45 != this.xPos) {
				if(p.getyPos() < this.yPos && p.getyPos() > this.yPos-50) {
					p.setyPos(this.yPos-50);
				}else if(p.getyPos() < this.yPos+50 && p.getyPos() > this.yPos) {
					p.setyPos(this.yPos+50);
				}
			}else{
				p.setxPos(this.xPos-50);
			}
		}
	}
	
}
