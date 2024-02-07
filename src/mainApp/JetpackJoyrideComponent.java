package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;


//hi
public class JetpackJoyrideComponent extends JComponent {
	private static final int PIXEL_SIZE = 50;
	private static final double MISSILE_SPAWN_PERCENTAGE_CHANCE = 1;
//	private static final int NORMAL_DELAY_COUNTDOWN = 1000;
//	private static final int HOMING_DELAY_COUNTDOWN = 250;
	private static final int TEXT_HEIGHT = 10;
	private static final int TEXT_WIDTH = 24;
	private static final int OFF_SCREEN = -50;
	private static final int RIGHT_SIDE_SCREEN = 1200;
	private static final int MIDDLE_OF_SCREEN = 600;
	private Player player = new Player(0, 0);
	private ArrayList<Collidable> collidablesToAdd = new ArrayList<Collidable>();
	private Missile normalMissile = new Missile(false);
	private Missile homingMissile = new Missile(true);
	private boolean emergencyShot = true;
	private boolean keyIsPressed = false;
	private String currentLevel;

	public void readFile(String filename) throws InvalidLevelFormatException {
		currentLevel = filename;
		int countLines = 0;
		File file = new File(filename);

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.length() != TEXT_WIDTH) {
					countLines = TEXT_HEIGHT + 1;
					break;
				}

				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == 'B') {
						Obstacle obstacle = new Obstacle(i * PIXEL_SIZE, countLines * PIXEL_SIZE, false);
						collidablesToAdd.add(obstacle);
					}
					if (line.charAt(i) == 'X') {
						Obstacle obstacle = new Obstacle(i * PIXEL_SIZE, countLines * PIXEL_SIZE, true);
						collidablesToAdd.add(obstacle);
					}
					if (line.charAt(i) == 'O') {
						Coin coin = new Coin(i * PIXEL_SIZE, countLines * PIXEL_SIZE);
						collidablesToAdd.add(coin);
					}
					if (line.charAt(i) == '_') {
						Barrier barrier = new Barrier(i * PIXEL_SIZE, countLines * PIXEL_SIZE);
						collidablesToAdd.add(barrier);
					}
					if (line.charAt(i) == 'H') {
						player.setxPos(i * PIXEL_SIZE);
						player.setyPos(countLines * PIXEL_SIZE);
					}
				}
				countLines++;
			}
			scanner.close();
			if (countLines == TEXT_HEIGHT) {
				normalMissile.setxPos(OFF_SCREEN);
				homingMissile.setxPos(OFF_SCREEN);
			} else {
				throw new InvalidLevelFormatException();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found: " + filename);
			e.printStackTrace();
		}
	}

	public void update() {
		handlePlayer();
		handleMissile();
		handleCollisions();
	}

	public ArrayList<Collidable> getCollidables() {
		return collidablesToAdd;
	}

	public void setKeyPressed(boolean pressed) {
		keyIsPressed = pressed;
	}

	public boolean checkGameOver() {
		return (player.getLives() == 0);
	}
	
	private void handleMissile() {
		if (Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE / 100 && normalMissile.getxPos() <= OFF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
//			normalMissile.setDelay(NORMAL_DELAY_COUNTDOWN);
		}
		if (Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE / 100 && homingMissile.getxPos() <= OFF_SCREEN) {
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
//			homingMissile.setDelay(HOMING_DELAY_COUNTDOWN);
		}
		if (emergencyShot && homingMissile.getxPos() <= OFF_SCREEN && normalMissile.getxPos() <= OFF_SCREEN
				&& player.getxPos() > MIDDLE_OF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		} else if (emergencyShot && homingMissile.getxPos() <= OFF_SCREEN && player.getxPos() > MIDDLE_OF_SCREEN) {
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		} else if (emergencyShot && normalMissile.getxPos() <= OFF_SCREEN && player.getxPos() > MIDDLE_OF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		}
		if(collidablesToAdd.size() == 0) {
			normalMissile.setxPos(OFF_SCREEN);
			homingMissile.setxPos(OFF_SCREEN);
		}
//		normalMissile.delayCountdown();
		normalMissile.move();
//		homingMissile.delayCountdown();
		homingMissile.move();
		homingMissile.homesIn(player.getxPos(), player.getyPos());
	}

	private void handleCollisions() {
		boolean tookDamage = false;
		for (Collidable c : this.collidablesToAdd) {
			if (!c.shouldRemove() && c.overlaps(player) && c.doesGetterDamage()) {
				c.collideWith(player);
				tookDamage = true;
			}else if (!c.shouldRemove() && c.overlaps(player)) {
				c.collideWith(player);
			}
		}
		
		if (homingMissile.overlaps(player)) {
			homingMissile.collideWith(player);
			handleLevelReset();
		}
		if (normalMissile.overlaps(player)) {
			normalMissile.collideWith(player);
			handleLevelReset();
		}
		if(tookDamage) {
			handleLevelReset();
		}

		ArrayList<Collidable> collidablesToRemove = new ArrayList<Collidable>();
		
		for(Collidable object: collidablesToAdd){
			if(object.shouldRemove()){
				collidablesToRemove.add(object);
			}
		}
		
		for(Collidable object: collidablesToRemove){
			this.collidablesToAdd.remove(object);
		}
	}
	
	private void handleLevelReset() {
		collidablesToAdd.clear();
		try {
			readFile(currentLevel);
		} catch (InvalidLevelFormatException e) {
			System.err.println(e.getMessage());
			System.err.println("Moving to empty level");
			collidablesToAdd.clear(); 
		}
		
	}
	
	private void handlePlayer() {
		player.move();
		if (keyIsPressed) {
			player.fly();
		} else {
			player.fall();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.drawOn(g2);
		for (Collidable c : collidablesToAdd) {
			c.drawOn(g2);
		}
		normalMissile.drawOn(g2);
		homingMissile.drawOn(g2);
	}
}
