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
	
	/**
	 * ensures: intializes the xPos & yPos
	 * @param xPos the x-position of the upper left corner of Collidable
	 * @param yPos the y-position of the upper left corner of Collidable
	 */
	public Collidable(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	/**
	 * ensures: Collidable being drawn
	 * @param g the graphics that Collidable will to drawn onto
	 */
	public abstract void drawOn(Graphics g);
	/**
	 * ensures: returns the height of the Collidable
	 * @return the height
	 */
	protected abstract double getHeight();
	/**
	 * ensures: returns the width of the Collidable
	 * @return the width
	 */
	protected abstract double getWidth();
	/**
	 * ensures: makes something happen when colliding with the player
	 * @param p the player that Collidable collides with
	 */
	protected abstract void collideWith(Player p);
	
	/**
	 * ensures: returns if this Collidable should be removed
	 */
	public boolean shouldRemove() {
		return this.shouldRemove;
	}
	
	/**
	 * ensures: marks this Collidable to be removed
	 */
	public void markToRemove() {
		this.shouldRemove = true;
	}
	
	/**
	 * ensures: checks if this Collidable does damage
	 */
	public boolean doesGetterDamage() {
		return doesDamage;
	}
	
	/**
	 * ensures: allows to set Collidables to damaging
	 */
	public void setDamageTrue() {
		this.doesDamage = true;
	}
	
	/**
	 * ensures: gets a bounding box
	 * @return the bounding box
	 */
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.xPos, this.yPos, getWidth(), getHeight() );
	}

	/**
	 * ensures: checks for overlap against player
	 * @param other the player that Collidable may overlap
	 * @return if it overlaps
	 */
	public boolean overlaps(Player other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
	
	/**
	 * ensures: checks for overlap against another Collidable
	 * @param other the collidable that Collidable may overlap
	 * @return if it overlaps
	 */
	public boolean overlaps(Collidable other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
	
	/**
	 * ensures: gets the name of the class type
	 * @return the class name
	 */
	public String getClassName() {
		return this.getClass().getSimpleName();
	}
}
