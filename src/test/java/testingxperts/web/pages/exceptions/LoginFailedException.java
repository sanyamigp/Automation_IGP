package testingxperts.web.pages.exceptions;

public class LoginFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginFailedException(String message){
		 super(message);
	}
}
