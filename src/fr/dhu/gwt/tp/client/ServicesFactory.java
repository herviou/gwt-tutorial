package fr.dhu.gwt.tp.client;

import com.google.gwt.core.client.GWT;

import fr.dhu.gwt.tp.shared.services.LoginServices;

/**
 * Factory for creating GWT-RPC Services
 * @author David.Herviou
 */
public class ServicesFactory {
	
	static LoginServices.IfaceAsync loginService = GWT.create(LoginServices.Iface.class);
	
	/**
	 * get an instance of the LoginServices 
	 * @return the loginServices asynchronous instance
	 */
	public static LoginServices.IfaceAsync  getLoginServices() {
		return loginService;
	}

}
