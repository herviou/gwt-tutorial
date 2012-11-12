package fr.dhu.gwt.tp.client;

/**
 * Register all history token available in this application
 * @author David.Herviou
 *
 */
public class HistoryToken {

	/**
	 * Token for login widget
	 */
	public static final String TOK_LOGIN = "/login/name=";

	/**
	 * useful for UIBinder
	 * @return
	 */
	public static String getTokLogin() {
		return TOK_LOGIN;
	}
	
	/**
	 * Token for register widget
	 */
	public static final String TOK_REGISTER = "/register";

	/**
	 * useful for UIBinder
	 * @return
	 */
	public static String getTokRegister() {
		return TOK_REGISTER;
	}

	/**
	 * 
	 */
	public static final String TOK_MAIN = "/mainspace";

	/**
	 * useful for UIBinder
	 * @return
	 */
	public static String getTokMain() {
		return TOK_MAIN;
	}
}
