package exception;

public class DuplicateCompanyNameException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateCompanyNameException(String companyName) {
		System.out.println("Company name \"" + companyName + "\" has been registered");
	}
}
