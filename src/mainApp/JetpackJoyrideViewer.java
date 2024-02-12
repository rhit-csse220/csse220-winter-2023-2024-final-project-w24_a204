package mainApp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

//hi
public class JetpackJoyrideViewer {
	
	private static final int VERTICAL_FRAME_BORDER = 37;
	private static final int HORIZONTAL_FRAME_BORDER = 14;
	private static final int LEVEL_MAX = 4;
	private static final int LEVEL_MIN = 1;
	private static final int DELAY = 50;
	private int fileNum = 1;
	private boolean gameOver = false;
	private int coinCount = 0;
	private int lifeCount;
	
	public void ScreenMain() {
		JFrame frame = new JFrame();
		frame.setTitle("Jetpack Joyride");
		frame.setSize(1200 + HORIZONTAL_FRAME_BORDER, 500 + VERTICAL_FRAME_BORDER);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JetpackJoyrideComponent component = new JetpackJoyrideComponent();
		JLabel label = new JLabel("Coins: " + coinCount);
		JLabel labelLife = new JLabel();
		label.setFont(new Font(null, Font.PLAIN, 30));
		labelLife.setFont(new Font(null, Font.PLAIN, 30));
		try {
			component.readFile("level/level1.txt", fileNum);
		}catch(InvalidLevelFormatException e) {
			System.err.println(e.getMessage());
			System.err.println("Moving to empty level");
			component.getCollidables().clear(); 
			
		}
		component.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				String filename = ("level/level" + fileNum + ".txt");;
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
						component.getCollidables().clear();
						component.readFile(filename, fileNum);
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 32) {
					component.setKeyPressed(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 32) {
					component.setKeyPressed(true);
				}
			}
		});
		frame.add(label);
		frame.add(labelLife);
		frame.add(component, BorderLayout.CENTER);
		
		Timer t = new Timer(DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(gameOver) {
					//currently pauses the game indefinitely
					component.getCollidables().clear();
					try {
						component.readFile("level/gameOver.txt", 1);
						label.setFont(new Font(null, Font.PLAIN, 20));
						label.setBounds(5, 0, 140, 50);
						labelLife.setText("");
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				}else if(fileNum > LEVEL_MAX) {
					try {
						component.readFile("level/gameOver.txt", 1);
						label.setFont(new Font(null, Font.PLAIN, 20));
						label.setBounds(5, 0, 140, 50);
						labelLife.setText("");
					} catch (InvalidLevelFormatException r) {
						System.err.println(r.getMessage());
						System.err.println("Moving to empty level");
						component.getCollidables().clear();
					}
				}else {
					component.grabFocus();
					gameOver = component.checkGameOver();
					label.setBounds(5, 50, 140, 50);
					labelLife.setBounds(5, 100, 140, 50);
					labelLife.setText("Lives: " + component.checkLives());
				}
				component.repaint();
				frame.repaint();
				label.setText("Coins: " + coinCount);
				label.repaint();
				labelLife.repaint();
				component.update();
				coinCount = component.checkCoins();
			}
			
		});
		
		
		t.start();
		
		
		frame.setVisible(true);
		
	}
}
