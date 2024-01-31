package mainApp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.Timer;

//hi
public class JetpackJoyrideViewer {
	
	private static final int FRAME_BORDER = 37;
	
	public void ScreenMain() {
		JFrame frame = new JFrame();
		frame.setTitle("Jetpack Joyride");
		frame.setSize(1200, 500 + FRAME_BORDER);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JetpackJoyrideComponent component = new JetpackJoyrideComponent();
		try {
		component.readFile("level/level1.txt");
		}catch(IllegalArgumentException e) {
			System.err.println("Illegal File Input Exception");
			System.err.println("File has to be 10x24");
		}
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
