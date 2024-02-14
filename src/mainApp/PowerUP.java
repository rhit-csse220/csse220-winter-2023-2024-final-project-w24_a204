package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PowerUP extends Collidable {

	private static final int RADIUS = 25;
	private static final Color LIFE_COLOR = new Color(255, 102, 102);
	private static final Color SHIELD_COLOR = new Color(51, 204, 255);
	private double powerSelector = 0;
	
	public PowerUP(double xPos, double yPos) {
		super((int)xPos, (int)yPos);
		powerSelector = Math.random();
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double powerup = new Ellipse2D.Double(xPos, yPos, 2*RADIUS, 2*RADIUS);
		if(powerSelector > .5) {
			g2.setColor(LIFE_COLOR);
		}else {
			g2.setColor(SHIELD_COLOR);
		}
		g2.fill(powerup);
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
		if(powerSelector > .5) {
			p.gainLife();
		}else if (powerSelector <= .5 && !p.checkShielded()){
			p.shieldToggle();
		}
		//System.out.println(p.getCoins());
	}
	
	protected void collideWith(Collidable c) {
		// TODO Auto-generated method stub
		this.markToRemove();
		//System.out.println(p.getCoins());
	}
	
	@Override
	public String getClassName() {
		return this.getClass().getSimpleName();
	}
}
