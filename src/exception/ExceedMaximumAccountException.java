package exception;

import common.*;

public class ExceedMaximumAccountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExceedMaximumAccountException() {
		System.out.println("Exceed maximum account number (" + Convention.MAXIMUM_ACCOUNT_NUMBER + ")!");
	}
}
