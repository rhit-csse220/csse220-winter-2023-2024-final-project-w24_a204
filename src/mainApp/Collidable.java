package mainApp;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;


public abstract class Collidable {
	
	protected double xPos;
	protected double yPos;
	private boolean shouldRemove;
	private boolean doesDamage = false;
	
	public Collidable(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public abstract void drawOn(Graphics g);

	protected abstract double getHeight();

	protected abstract double getWidth();
	
	protected abstract void collideWith(Player p);
	
	public boolean shouldRemove() {
		return this.shouldRemove;
	}
	
	public void markToRemove() {
		this.shouldRemove = true;
	}
	
	public boolean doesGetterDamage() {
		return doesDamage;
	}
	
	public void setDamageTrue() {
		this.doesDamage = true;
	}
	
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, getWidth(), getHeight() );
	}

	public boolean overlaps(Player other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
}
