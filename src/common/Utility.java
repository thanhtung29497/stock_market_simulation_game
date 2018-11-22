package common;

public class Utility {
	public static int COMPUTER_INDEX = 0;
	public static String generateComputerName() {
		Utility.COMPUTER_INDEX++;
		return Convention.COMPUTER_PLAYER_NAME + Utility.COMPUTER_INDEX;
	}
}
