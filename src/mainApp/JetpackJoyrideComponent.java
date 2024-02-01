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
	private static final int LEVEL_MAX = 2;
	private static final int LEVEL_MIN = 1;
	private Player player = new Player(0, 0);
	private ArrayList<Collidable> collidables = new ArrayList<Collidable>();
	private String filename = ("level/level1.txt");;
	private int fileNum = 1;
	private boolean keyIsPressed = false;

	public void AddPlayer() {

	}

	public void AddCollidables() {

	}

	public void loadLevel() {

	}

	public void readFile() {
		int countLines = 0;
		File file = new File(filename);

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				if (line.length() != 24) {
					countLines = 11;
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
			if (countLines == 10) {
				System.out.println("Success!");
			} else {
				throw new IllegalArgumentException();
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
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.drawOn(g2);
		for (Collidable c : collidables) {
			c.drawOn(g2);
		}
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyChar() == 'u') {
					if (fileNum < LEVEL_MAX) {
						fileNum++;
						filename = ("level/level" + fileNum + ".txt");
					}
				} else if (e.getKeyChar() == 'd') {
					if (fileNum > LEVEL_MIN) {
						fileNum--;
						filename = ("level/level" + fileNum + ".txt");
					}
				}
				if (e.getKeyChar() == 'u' || e.getKeyChar() == 'd') {
					try {
						collidables.clear();
						readFile();
					} catch (IllegalArgumentException r) {
						System.err.println("Illegal File Input Exception");
						System.err.println("File has to be 10x24 characters");
						System.err.println("Moving to empty level");
						collidables.clear();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 32) {
					keyIsPressed = false;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 32) {
					keyIsPressed = true;
				}
			}
		});
	}
}
