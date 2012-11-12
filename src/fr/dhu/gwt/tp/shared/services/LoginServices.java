package fr.dhu.gwt.tp.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.dhu.gwt.tp.shared.model.Person;

/**
 * Services for Login and 
 * @author David.Herviou
 *
 */
public class LoginServices {

	/**
	 * Synchronous interface of services
	 * @author David.Herviou
	 * URI is gwtmodulename/loginServices (check web.xml for mapping)
	 */
	@RemoteServiceRelativePath("loginServices")
	public static interface Iface extends RemoteService {

		/**
		 * Check if the person is connected
		 * @return true if user is already connected
		 */
		public Boolean isConnected() throws LoginServicesException;
		
		/**
		 * Connect the given person (create a session with connected attribute)
		 * @param login the user login
		 * @param password the user password
		 * @return the person only if the login/password is Ok;
		 * @throws LoginServicesException with code {@link LoginServicesException.CODE.USER_ACCOUNT_DOES_NOT_EXIST}
		 * when trying to connect an unknow user. Raised a {@link LoginServicesException.CODE.LOGIN_PASSWORD_FAILURE}
		 * if couple login/password failed
		 */
		public Person connect(String login, String password) throws LoginServicesException;
		
		
		/**
		 * Disconnect the user (remove its session)
		 */
		public void disconnect() throws LoginServicesException;
		
		/**
		 * Register a person with password
		 * the login of person will be used as primary key for storage
		 * @param person the person to register
		 * @param password the password associated with the person
		 * @return the person which has been register only if the registration has worked
		 * @throws LoginServicesException if the registration failed or the user is already registered
		 */
		public Person register(Person person, String password) throws LoginServicesException;
	}
	
	
	/**
	 * Asynchronous interface of synchronous services
	 * @author David.Herviou
	 *
	 */
	public static interface IfaceAsync {

		void isConnected(AsyncCallback<Boolean> callback);

		void connect(String login, String password, AsyncCallback<Person> callback);

		void disconnect(AsyncCallback<Void> callback);

		void register(Person person, String password, AsyncCallback<Person> callback) ;
	}
	
}
