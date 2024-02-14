package mainApp;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;


/**
 * Class: Collidable
 * @author A204
 * Purpose: Acts as a superclass for everything that collides with the player
 * during all times of the gameplay.
 * For example: 
 * 
 * Collidable collidable = new Collidable(50, 50);
 * 
 */
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
	
	public boolean overlaps(Collidable other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
	
	public String getClassName() {
		return this.getClass().getSimpleName();
	}
}
