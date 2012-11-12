package fr.dhu.gwt.tp.server.servlet;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.dhu.gwt.tp.server.dao.PersonDAO;
import fr.dhu.gwt.tp.shared.model.Person;
import fr.dhu.gwt.tp.shared.services.LoginServices;
import fr.dhu.gwt.tp.shared.services.LoginServicesException;
import fr.dhu.gwt.tp.shared.services.LoginServicesException.CODE;


/**
 * Servlet entry point for loginService
 * @author David.Herviou
 */
public class LoginServlet extends RemoteServiceServlet implements LoginServices.Iface {

	private static final long serialVersionUID = -1400270762738075791L;

	/**
	 * Check if the person is connected
	 * @return Person if user is already connected
	 */
	@Override
	public Person isConnected() throws LoginServicesException {
		HttpSession session = getThreadLocalRequest().getSession();
		if(session == null){
			throw new LoginServicesException(LoginServicesException.CODE.NO_ACTIVE_SESSION);
		}
		
		Object connected = session.getAttribute("connected");
		if( connected == null ) {
			throw new LoginServicesException(LoginServicesException.CODE.NO_ACTIVE_SESSION);
		}
		
		return (Person)connected;
	}

	/**
	 * Connect the given person (create a session with connected attribute)
	 * @param login the user login
	 * @param password the user password
	 * @return the person only if the login/password is Ok;
	 * @throws LoginServicesException with code {@link LoginServicesException.CODE.USER_ACCOUNT_DOES_NOT_EXIST}
	 * when trying to connect an unknow user. Raised a {@link LoginServicesException.CODE.LOGIN_PASSWORD_FAILURE}
	 * if couple login/password failed
	 */
	@Override
	public Person connect(String login, String password) throws LoginServicesException {

		//Check login/password adequation
		//
		Person person = PersonDAO.getInstance().searchPerson(login);
		
		if(person == null) {
			throw new LoginServicesException(CODE.USER_ACCOUNT_DOES_NOT_EXIST);
		}
		
		if( PersonDAO.getInstance().checkPassword(login,password) ){
			// if login/password OK then declare the connected status in session
			//
			HttpSession session = getThreadLocalRequest().getSession();
			session.setAttribute("connected",person);
			return person;
		} else {
			throw new LoginServicesException(CODE.LOGIN_PASSWORD_FAILURE);
		}

	}

	
	/**
	 * Disconnect the user (remove its session)
	 */
	@Override
	public void disconnect() throws LoginServicesException {
		HttpSession session = getThreadLocalRequest().getSession();
		if( session != null ) {
			session.removeAttribute("connected");
		}
	}

	/**
	 * Register a person with password
	 * the login of person will be used as primary key for storage
	 * @param person the person to register
	 * @param password the password associated with the person
	 * @return the person which has been register only if the registration has worked
	 * @throws LoginServicesException if the registration failed or the user is already registered
	 */
	@Override
	public Person register(Person person, String password) throws LoginServicesException {
		
		if( PersonDAO.getInstance().searchPerson(person.getLogin()) != null ) {
			throw new LoginServicesException(CODE.USER_ACCOUNT_ALREADY_EXIST);
		}
		
		if ( PersonDAO.getInstance().addPerson(person, password) ){
			return person;
		} else {
			throw new LoginServicesException(CODE.USER_ACCOUNT_ALREADY_EXIST);
		}
		
	}
	

}
