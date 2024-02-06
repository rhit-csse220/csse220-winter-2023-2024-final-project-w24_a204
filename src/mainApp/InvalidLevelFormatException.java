package mainApp;

public class InvalidLevelFormatException extends Exception {
	public InvalidLevelFormatException() {
		super("Illegal File Input Exception\n"
		+ "File has to be 10x24 characters");
	}
}
