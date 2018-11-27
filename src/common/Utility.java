package common;

public class Utility {
	public static int COMPUTER_INDEX = 0;
	public static String generateComputerName() {
		Utility.COMPUTER_INDEX++;
		return Convention.COMPUTER_PLAYER_NAME + Utility.COMPUTER_INDEX;
	}
	
	public static int BID_INDEX = 0;
	public static int generateBidId() {
		Utility.BID_INDEX++;
		return Utility.BID_INDEX;
	}
	
	public static int ACCOUNT_INDEX = 0;
	public static int generateAccountId() {
		Utility.ACCOUNT_INDEX++;
		return Utility.ACCOUNT_INDEX;
	}
}
