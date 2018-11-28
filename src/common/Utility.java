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
	
	public static String PLAYER_ID_PREFIX = "player";
	public static Integer PLAYER_INDEX = 0;
	
	public static String generateAccountId() {
		Utility.PLAYER_INDEX++;
		return PLAYER_ID_PREFIX + Utility.PLAYER_INDEX.toString();
	}
	
	public static String COMPANY_ID_PREFIX = "company";
	public static Integer COMPANY_INDEX = 0;
	
	public static String generateCompanyId() {
		Utility.COMPANY_INDEX++;
		return Utility.COMPANY_ID_PREFIX + Utility.COMPANY_INDEX.toString();
	}
	
	
	public static Boolean isPlayerId(String id) {
		return id.startsWith(Utility.PLAYER_ID_PREFIX);
	}
}
