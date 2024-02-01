package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

//hi
public class JetpackJoyrideViewer {
	
	private static final int FRAME_BORDER = 37;
	private static final int LEVEL_MAX = 2;
	private static final int LEVEL_MIN = 1;
	private int fileNum = 1;
	
	public void ScreenMain() {
		JFrame frame = new JFrame();
		frame.setTitle("Jetpack Joyride");
		frame.setSize(1200 + 14, 500 + FRAME_BORDER);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JetpackJoyrideComponent component = new JetpackJoyrideComponent();
		try {
			component.readFile("level/level1.txt");
		}catch(IllegalArgumentException e) {
			System.err.println("Illegal File Input Exception");
			System.err.println("File has to be 10x24 characters");
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
						component.readFile(filename);
					} catch (IllegalArgumentException r) {
						System.err.println("Illegal File Input Exception");
						System.err.println("File has to be 10x24 characters");
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
		frame.add(component, BorderLayout.CENTER);
		
		Timer t = new Timer(50, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				component.repaint();
				frame.repaint();
				component.grabFocus();
				component.update();
			}
			
		});
		
		
		t.start();
		
		
		frame.setVisible(true);
		
	}
	
	public void updateScreen() {
		
	}
}
