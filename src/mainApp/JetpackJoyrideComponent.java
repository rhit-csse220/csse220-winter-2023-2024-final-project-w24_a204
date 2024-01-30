package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;

//hi
public class JetpackJoyrideComponent extends JComponent{
	private static final int PIXEL_SIZE = 50;
	
	private ArrayList<Collidable> collidables = new ArrayList<Collidable>();

	public void AddPlayer() {
		
	}
	
	public void AddCollidables() {
		
	}
	
	public void loadLevel() {
		
	}
	
	public void readFile(String filename) {
		int countLines = 0;
		File file = new File(filename);
		
		
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				if(line.length() != 24) {
					countLines = 11;
					break;
				}
				countLines++;
				for(int i = 0; i < line.length(); i++) {
					if(line.charAt(i)== 'B') {
						Obstacle obstacle = new Obstacle(i*PIXEL_SIZE, countLines*PIXEL_SIZE, false);
						collidables.add(obstacle);
					}
				}
			}
			scanner.close();
			if(countLines == 10) {
				System.out.println("Success!");
			} else {
				throw new IllegalArgumentException();
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found: " + filename);
			e.printStackTrace();
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		for(Collidable c : collidables) {
			c.drawOn(g2);
		}
	}
}
