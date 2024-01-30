package mainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;

//hi
public class JetpackJoyrideViewer {
	
	public void ScreenMain() {
		JFrame frame = new JFrame();
		frame.setTitle("Jetpack Joyride");
		frame.setSize(1200, 500);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void ReadFile(String filename) {
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
//				for(int i = 0; i < line.length(); i++) {
//					if(line.charAt(i)== '.') {
//						
//					}
//				}
			}
			scanner.close();
			if(countLines == 10) {
				System.out.println("Success!");
			} else {
				System.err.println("InvalidLevelFormatException");
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found: " + filename);
			e.printStackTrace();
		}
	}
	
	public void updateScreen() {
		
	}
}
