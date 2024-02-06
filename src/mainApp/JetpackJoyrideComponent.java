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
	private static final int OF_SCREEN = -50;
	private static final int RIGHT_SIDE_SCREEN = 1200;
	private static final int BOTTOM_OF_SCREEN = 600;
	private Player player = new Player(0, 0);
	private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
	private Missile normalMissile = new Missile(false);
	private Missile homingMissile = new Missile(true);
	private boolean emergencyShot = true;
	private boolean keyIsPressed = false;

	public void AddPlayer() {

	}

	public void AddCollidables() {

	}

	public void loadLevel() {

	}

	public void readFile(String filename) throws InvalidLevelFormatException {
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
						collidables.add(obstacle);
					}
					if (line.charAt(i) == 'X') {
						Obstacle obstacle = new Obstacle(i * PIXEL_SIZE, countLines * PIXEL_SIZE, true);
						collidables.add(obstacle);
					}
					if (line.charAt(i) == 'O') {
						Coin coin = new Coin(i * PIXEL_SIZE, countLines * PIXEL_SIZE);
						collidables.add(coin);
					}
					if (line.charAt(i) == '_') {
						Barrier barrier = new Barrier(i * PIXEL_SIZE, countLines * PIXEL_SIZE);
						collidables.add(barrier);
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
				normalMissile.setxPos(OF_SCREEN);
				homingMissile.setxPos(OF_SCREEN);
			} else {
				throw new InvalidLevelFormatException();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found: " + filename);
			e.printStackTrace();
		}
	}

	public void update() {
		player.move();
		if(keyIsPressed) {
			player.fly();
		}else {
			player.fall();
		}
		if(Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE/100 && normalMissile.getxPos() <= OF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
//			normalMissile.setDelay(NORMAL_DELAY_COUNTDOWN);
		}
		if(Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE/100 && homingMissile.getxPos() <= OF_SCREEN) {
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
//			homingMissile.setDelay(HOMING_DELAY_COUNTDOWN);
		}
		if(emergencyShot && homingMissile.getxPos() <= OF_SCREEN && normalMissile.getxPos() <= OF_SCREEN
				&& player.getxPos() > BOTTOM_OF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		}else if(emergencyShot && homingMissile.getxPos() <= OF_SCREEN && player.getxPos() > BOTTOM_OF_SCREEN) {
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		}else if(emergencyShot && normalMissile.getxPos() <= OF_SCREEN && player.getxPos() > BOTTOM_OF_SCREEN) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
			emergencyShot = false;
		}
//		normalMissile.delayCountdown();
		normalMissile.move();
//		homingMissile.delayCountdown();
		homingMissile.move();
		homingMissile.homesIn(player.getxPos(), player.getyPos());
	}
	
	public ArrayList<Collidable> getCollidables() {
		return collidables;
	}
	
	public void setKeyPressed(boolean pressed) {
		keyIsPressed = pressed;
	}
	
	public boolean checkGameOver(){
		return (player.getLives() == 0);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.drawOn(g2);
		for (Collidable c : collidables) {
			c.drawOn(g2);
		}
		normalMissile.drawOn(g2);
		homingMissile.drawOn(g2);
	}
}
