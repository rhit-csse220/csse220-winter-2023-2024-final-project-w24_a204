package mainApp;

import java.awt.event.KeyListener;

/**
 * Class: MainApp
 * @author A204
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	
	
	private void runApp() {
		System.out.println("Jetpack Joyride!");
	} // runApp

	/**
	 * ensures: runs the application
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		JetpackJoyrideViewer viewer = new JetpackJoyrideViewer();
		viewer.ScreenMain();
		mainApp.runApp();
	} // main

}