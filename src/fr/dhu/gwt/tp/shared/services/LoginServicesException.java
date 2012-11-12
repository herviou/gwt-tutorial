package fr.dhu.gwt.tp.shared.services;

/**
 * Exception thrown by {@link LoginServices}
 * @author David.Herviou
 */
public class LoginServicesException extends Exception {

	private static final long serialVersionUID = 1432987431850609562L;

	/**
	 * error code that is the origin of this exception
	 */
	public static enum CODE {
		UNKNOWN,
		LOGIN_PASSWORD_FAILURE,
		USER_ACCOUNT_DOES_NOT_EXIST,
		USER_ACCOUNT_ALREADY_EXIST,
		NO_ACTIVE_SESSION,
	};

	private CODE code = CODE.UNKNOWN;
	
	
	public LoginServicesException() {
	}
	
	public LoginServicesException(CODE code) {
		this.code = code;
	}
	
	
	public CODE getCode() {
		return code;
	}
	
	
}
