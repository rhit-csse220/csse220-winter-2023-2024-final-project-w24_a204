package mainApp;

/**
 * Class: InvalidLevelFormatException
 * @author A204
 * Purpose: Used to alert the user that a level has an incorrect format
 */
public class InvalidLevelFormatException extends Exception {
	public InvalidLevelFormatException() {
		super("Illegal File Input Exception\n"
		+ "File has to be 10x24 characters");
	}
}
