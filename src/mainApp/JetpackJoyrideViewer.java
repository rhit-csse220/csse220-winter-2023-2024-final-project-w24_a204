package mainApp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Class: JetpackJoyrideViewer
 * 
 * @author A204 Purpose: Create a screen for the game to be played on,
 *         initialize a component to run the logic of the game, initializes a
 *         timer as well as KeyListeners. For example:
 * 
 *         JetpackJoyrideViewer viewer = new JetpackJoyrideViewer();
 */
public class JetpackJoyrideViewer {

	private static final int VERTICAL_FRAME_BORDER = 37;
	private static final int HORIZONTAL_FRAME_BORDER = 14;
	private static final int LEVEL_MAX = 6;
	private static final int LEVEL_MIN = 1;
	private static final int DELAY = 50;
	private int fileNum = 1;
	private boolean gameOver = false;
	private int coinCount = 0;

	/**
	 * ensures: puts everything together into the game
	 */
	public void ScreenMain() {
		JFrame frame = new JFrame();
		frame.setTitle("Jetpack Joyride");
		frame.setSize(1200 + HORIZONTAL_FRAME_BORDER, 500 + VERTICAL_FRAME_BORDER);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JetpackJoyrideComponent component = new JetpackJoyrideComponent();
		JLabel label = new JLabel("Coins: " + coinCount);
		JLabel labelLife = new JLabel();
		JLabel labelWin = new JLabel();
		label.setFont(new Font(null, Font.PLAIN, 25));
		labelLife.setFont(new Font(null, Font.PLAIN, 25));
		try {
			component.readFile("level/level1.txt", fileNum);
		} catch (InvalidLevelFormatException e) {
			System.err.println(e.getMessage());
			System.err.println("Moving to empty level");
			component.getCollidables().clear();

		}

		File song = new File("music/Theme.wav");
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(song);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		component.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				String filename = ("level/level" + fileNum + ".txt");
				;
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
				} else if (e.getKeyChar() == 'q') {
					if (fileNum > LEVEL_MAX || gameOver) {
						System.exit(0);
					}
				} else if (e.getKeyChar() == 'r') {
					if (fileNum > LEVEL_MAX || gameOver) {
						component.handleFullRestart();
						gameOver = false;
						fileNum = 1;
						filename = "level/level1.txt";
						try {
							component.getCollidables().clear();
							component.readFile(filename, fileNum);
						} catch (InvalidLevelFormatException r) {
							System.err.println(r.getMessage());
							System.err.println("Moving to empty level");
							component.getCollidables().clear();
						}
					}
				}
				if (e.getKeyChar() == 'u' || e.getKeyChar() == 'd') {
					if (fileNum <= LEVEL_MAX) {
						try {
							component.getCollidables().clear();
							component.readFile(filename, fileNum);
						} catch (InvalidLevelFormatException r) {
							System.err.println(r.getMessage());
							System.err.println("Moving to empty level");
							component.getCollidables().clear();
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 32) {
					component.setKeyPressed(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 32) {
					component.setKeyPressed(true);
				}
			}
		});
		frame.add(label);
		frame.add(labelLife);
		frame.add(labelWin);
		frame.add(component, BorderLayout.CENTER);

		Timer t = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int currentLevel = fileNum;
				if (component.checkNextLevel()) {
					fileNum++;
				}
				if (gameOver) {
					component.getCollidables().clear();
					try {
						component.readFile("level/gameOver.txt", 1);
						label.setFont(new Font(null, Font.PLAIN, 50));
						labelWin.setFont(new Font(null, Font.PLAIN, 20));
						label.setBounds(300, 300, 800, 100);
						labelWin.setBounds(300, 350, 700, 100);
						label.setText("Total Coins Earned: " + coinCount);
						labelWin.setText("<html>Press q to Quit<br/>Press r to Restart</html>");
						labelLife.setText("");
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				} else if (fileNum > LEVEL_MAX) {
					component.handleWin();
					try {
						component.readFile("level/win.txt", 1);
						label.setFont(new Font(null, Font.PLAIN, 50));
						labelWin.setFont(new Font(null, Font.PLAIN, 20));
						label.setBounds(300, 300, 800, 100);
						labelWin.setBounds(300, 350, 700, 100);
						label.setText("Total Coins Earned: " + coinCount);
						labelWin.setText("<html>Press q to Quit<br/>Press r to Restart</html>");
						labelLife.setText("");
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				} else if (currentLevel < fileNum) {
					try {
						component.getCollidables().clear();
						component.readFile("level/level" + fileNum + ".txt", fileNum);
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				} else {
					component.grabFocus();
					gameOver = component.checkGameOver();
					label.setFont(new Font(null, Font.PLAIN, 25));
					label.setBounds(5, 50, 145, 50);
					labelLife.setBounds(5, 100, 140, 50);
					label.setText("Coins: " + coinCount);
					labelLife.setText("Lives: " + component.checkLives());
					labelWin.setText("");
				}
				component.repaint();
				frame.repaint();
				label.repaint();
				labelLife.repaint();
				component.update();
				coinCount = component.checkCoins();
				if (fileNum == 4) {
					component.checkSecret();
				}
			}

		});

		t.start();

		frame.setVisible(true);

	}
}
