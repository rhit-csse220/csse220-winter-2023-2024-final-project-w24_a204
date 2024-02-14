package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

/**
 * Class: JetpackJoyrideComponent
 * @author A204
 * Purpose: Used to read text files and convert them into collidables that are drawn
 * on the screen, also respawns the missiles and resets the level if needed. 
 * For example: 
 * 
 * JetpackJoyrideComponent component = new JetpackJoyrideComponent();
 * 
 */
public class JetpackJoyrideComponent extends JComponent {
	private static final int PIXEL_SIZE = 50;
	private static final double MISSILE_SPAWN_PERCENTAGE_CHANCE = 1;
	private static final int TEXT_HEIGHT = 10;
	private static final int TEXT_WIDTH = 24;
	private static final int OFF_SCREEN = -50;
	private static final int RIGHT_SIDE_SCREEN = 1200;
	private static final int MIDDLE_OF_SCREEN = 600;
	private static final int BOTTOM_OF_SCREEN = 500;
	private Player player = new Player(0, 0);
	private ArrayList<Collidable> collidablesToAdd = new ArrayList<Collidable>();
	private Missile normalMissile = new Missile(false);
	private Missile homingMissile = new Missile(true);
	private boolean powerUPExists = false;
	private boolean emergencyShot = true;
	private boolean keyIsPressed = false;
	private String currentLevel;
	private int currentLevelNum;

	/**
	 * ensures: reads a file and adds the objects to an ArrayList to be made later
	 * @param filename the file name that readFile reads from
	 * @param fileNum the file number
	 * @throws InvalidLevelFormatException
	 */
	public void readFile(String filename, int fileNum) throws InvalidLevelFormatException {
	
		currentLevel = filename;
		currentLevelNum = fileNum;
		emergencyShot = true;
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
						Barrier barrier = new Barrier(i * PIXEL_SIZE, countLines * PIXEL_SIZE, false);
						collidablesToAdd.add(barrier);
					}
					if (line.charAt(i) == 'S') {
						Barrier barrier = new Barrier(i * PIXEL_SIZE, countLines * PIXEL_SIZE, true);
						collidablesToAdd.add(barrier);
					}
					if (line.charAt(i) == 'H') {
						player.setxPos(i * PIXEL_SIZE);
						player.setyPos(countLines * PIXEL_SIZE);
					}
				}
				countLines++;
			}
			if(fileNum%2 == 0 && !filename.equals("level/gameOver.txt") && !filename.equals("level/win.txt") && !filename.equals("level/secretLevel.txt")) {
				PowerUP power = new PowerUP((RIGHT_SIDE_SCREEN - 3*PIXEL_SIZE) * Math.random() + 2*PIXEL_SIZE, (BOTTOM_OF_SCREEN - 3*PIXEL_SIZE) * Math.random() + PIXEL_SIZE);
				collidablesToAdd.add(power);
				powerUPExists = true;
			} else {
				powerUPExists = false;
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

	/**
	 * ensures: updates every component
	 */
	public void update() {
		if (player.getLives() > 0) {
			handlePlayer();
			if(currentLevel != "level/secretLevel.txt") {
				handleMissile();
			}
			handleCollisions();
		} else {
			handlePlayer();
		}
	}

	/**
	 * ensures: returns an array list of the collidables
	 * @return an array list of collidables
	 */
	public ArrayList<Collidable> getCollidables() {
		return collidablesToAdd;
	}

	/**
	 * ensures: sets the key to if it is pressed
	 */
	public void setKeyPressed(boolean pressed) {
		keyIsPressed = pressed;
	}

	/**
	 * ensures: checks if the game is over
	 * @return if the player is out of lives
	 */
	public boolean checkGameOver() {
		return (player.getLives() == 0);
	}

	/**
	 * ensures: handles the missiles, making them appear, move, and disappear
	 */
	private void handleMissile() {
		if (Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE / 100 && normalMissile.getxPos() <= OFF_SCREEN) {
			if(currentLevelNum > 1) {
			normalMissile.setxPos(RIGHT_SIDE_SCREEN);
			}
		}
		if (Math.random() < MISSILE_SPAWN_PERCENTAGE_CHANCE / 100 && homingMissile.getxPos() <= OFF_SCREEN) {
			if(currentLevelNum > 2) {
			homingMissile.setxPos(RIGHT_SIDE_SCREEN);
			}
		}
		if (emergencyShot && normalMissile.getxPos() <= OFF_SCREEN && player.getxPos() > MIDDLE_OF_SCREEN) {
			if(currentLevelNum > 1) {
				normalMissile.setxPos(RIGHT_SIDE_SCREEN);
				if(currentLevelNum > 2 && homingMissile.getxPos() <= OFF_SCREEN) {
					homingMissile.setxPos(RIGHT_SIDE_SCREEN);
				}
				emergencyShot = false;
			}
			
		} else if (emergencyShot && homingMissile.getxPos() <= OFF_SCREEN && player.getxPos() > MIDDLE_OF_SCREEN) {
			if(currentLevelNum > 2) {
				homingMissile.setxPos(RIGHT_SIDE_SCREEN);
				emergencyShot = false;
			}
			
		} else if (emergencyShot && normalMissile.getxPos() <= OFF_SCREEN && player.getxPos() > MIDDLE_OF_SCREEN) {
			if(currentLevelNum > 1) {
				normalMissile.setxPos(RIGHT_SIDE_SCREEN);
				emergencyShot = false;
			}
		}
		if (collidablesToAdd.size() == 0) {
			if(currentLevelNum > 1) {
				normalMissile.setxPos(OFF_SCREEN);
				if(currentLevelNum > 2) {
					homingMissile.setxPos(OFF_SCREEN);
				}
			}
		}
		if(currentLevelNum > 1) {
			normalMissile.move();
			if(currentLevelNum > 2) {
				homingMissile.move();
				homingMissile.homesIn(player.getxPos(), player.getyPos());
			}
		}
	}

	/**
	 * ensures: handles the collisions of all collidables and player, removing collidables
	 * when necessary and dealing damage or granting powers
	 */
	private void handleCollisions() {
		boolean tookDamage = false;
		boolean hadPowerUp = powerUPExists;
		for (Collidable c : this.collidablesToAdd) {
			if (!c.shouldRemove() && c.overlaps(player) && c.doesGetterDamage()) {
				if(!player.checkShielded()) {
					tookDamage = true;
				}
				c.collideWith(player);
				break;
			} else if (!c.shouldRemove() && c.overlaps(player)) {
				c.collideWith(player);
			} else if (c.getClassName().equals("PowerUP") && !c.shouldRemove()) {
				PowerUP power = (PowerUP)c;
				for(Collidable o : this.collidablesToAdd) {
					if (!(o.getClassName().equals("PowerUP")) && power.overlaps(o)) {
						power.collideWith(o);
						powerUPExists = false;
					}
				}
			}
		}

		if (homingMissile.overlaps(player)) {
			if(!player.checkShielded()) {
				tookDamage = true;
			}else {
				homingMissile.setxPos(OFF_SCREEN);
			}
			homingMissile.collideWith(player);
		}
		if (normalMissile.overlaps(player)) {
			if(!player.checkShielded()) {
				tookDamage = true;
			}else {
				normalMissile.setxPos(OFF_SCREEN);
			}
			normalMissile.collideWith(player);
		}
		if (tookDamage) {
			handleLevelReset();
		}
		if(hadPowerUp && !powerUPExists) {
			collidablesToAdd.add(new PowerUP((RIGHT_SIDE_SCREEN - 3*PIXEL_SIZE) * Math.random() + 2*PIXEL_SIZE, (BOTTOM_OF_SCREEN - 3*PIXEL_SIZE) * Math.random() + PIXEL_SIZE));
			powerUPExists = true;
		}

		ArrayList<Collidable> collidablesToRemove = new ArrayList<Collidable>();

		for (Collidable object : collidablesToAdd) {
			if (object.shouldRemove()) {
				collidablesToRemove.add(object);
			}
		}

		for (Collidable object : collidablesToRemove) {
			this.collidablesToAdd.remove(object);
		}
	}

	/**
	 * ensures: handles the resetting of a level when player takes damage
	 */
	private void handleLevelReset() {
		collidablesToAdd.clear();
		try {
			readFile(currentLevel, currentLevelNum);
		} catch (InvalidLevelFormatException e) {
			System.err.println(e.getMessage());
			System.err.println("Moving to empty level");
			collidablesToAdd.clear();
		}

	}

	/**
	 * ensures: handles the players movement and their iframes
	 */
	private void handlePlayer() {
		if (player.getLives() > 0) {
			player.move();
			if (keyIsPressed) {
				player.fly();
			} else {
				player.fall();
			}
		} else {
			player.setxPos(OFF_SCREEN);
		} 
		player.iframeCountdown();
	}
	
	/**
	 * ensures: handles part of what happens when you win
	 */
	public void handleWin() {
		collidablesToAdd.clear();
		player.die();
	}
	
	/**
	 * ensures: handles a part of a full restart
	 */
	public void handleFullRestart() {
		player.restart();
	}
	
	/**
	 * ensures: checks and returns how much coins a player has
	 */
	public int checkCoins() {
		return player.getCoins();
	}
	
	/**
	 * ensures: checks and returns how much coins a player has
	 */
	public int checkLives() {
		return player.getLives();
	}
	
	/**
	 * ensures: checks and returns if player should move onto the next level
	 * @return if player should move to the next level
	 */
	public boolean checkNextLevel() {
		if(player.getxPos() >= RIGHT_SIDE_SCREEN-PIXEL_SIZE) {
			return true;
		}
		return false;
	}
	
	/**
	 * ensures: checks if player should move to the secret level
	 */
	public void checkSecret() {
		if(player.getyPos() < 5) {
			try {
				collidablesToAdd.clear();
				readFile("level/secretLevel.txt", 4);
			} catch (InvalidLevelFormatException e) {
				System.err.println(e.getMessage());
				System.err.println("Moving to empty level");
				collidablesToAdd.clear();
			}
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
