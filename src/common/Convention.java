package common;

import java.rmi.registry.Registry;

public class Convention {
	public static double INITIAL_BALANCE = 1000.0;
	public static final double BANK_INTEREST_RATE = 0.01;
	public static int INITIAL_SHARE_NUMBER = 1000;
	public static double INITIAL_SHARE_PRICE = 30;
	public static final int MAXIMUM_ACCOUNT_NUMBER = 20;
	public static final double STOCK_PERCENT_RANGE = 0.1;
	public static final int ACCOUNT_ACCUMULATION_PERIOD = 60000;
	public static int STOCK_EXCHANGE_SESSION = 60000;
	public static int GAME_DURATION = 30 * 60000;
	public static final String COMPUTER_PLAYER_NAME = "CP";
	public static final String COMPANY_NAME = "Company";
	public static final String COMPUTER_PLAYER_PASSWORD = "123";
	public static final String HOST_NAME = "stockmarketgame";
	public static final String URL = "rmi://" + Convention.HOST_NAME + ":" + Registry.REGISTRY_PORT;	
	public static final String BANK_SERVER_NAME = "bank";
	public static final String ACCOUNT_CONTROLLER_NAME = "account";
	public static final String BANK_CONTROLLER_NAME = "controller";
	public static final String STOCK_EXCHANGE_SERVER_NAME = "stockexchange";
	public static final String PLAYER_STOCK_CONTROLLER_NAME = "player";
	public static final String COMPANY_STOCK_CONTROLLER_NAME = "company";
	public static final int PLAYER_RETRIEVE_MESSAGE_PERIOD = 1000;
	public static final int COMPANY_RETRIEVE_MESSAGE_PERIOD = 1000;
	public static final int COMPUTER_RETRIEVE_MESSAGE_PERIOD = 1000;
	public static final int RETRIEVE_TIME_NUMBER_PERIOD = 60;
	public static final int STOCK_SERVER_UPDATING_TASK = 1000;
}
