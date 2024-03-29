package mainApp;

/**
 * Class: MainApp
 * 
 * @author A204 <br>
 *         Purpose: Top level class for CSSE220 Project containing main method
 *         <br>
 *         Restrictions: None
 */
public class MainApp {

	/**
	 * ensures: makes the viewer and runs it
	 */
	private void runApp() {
		JetpackJoyrideViewer viewer = new JetpackJoyrideViewer();
		viewer.ScreenMain();
	}

	/**
	 * ensures: runs the application
	 * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
	}

}