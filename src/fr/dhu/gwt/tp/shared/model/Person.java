package fr.dhu.gwt.tp.shared.model;

import java.io.Serializable;

/**
 * An object that represents a Person
 * @author David.Herviou
 */
public class Person implements Serializable{


	private static final long serialVersionUID = 8659971200055945050L;

	protected String firstName="";
	
	protected String lastName="";
	
	protected String login="";
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@Override
	public String toString() {
		return login+" "+firstName+" "+lastName;
	}
	
	@Override
	public boolean equals(Object obj) {
		Person comparee = (Person)obj;
		if(obj == null) {
			return false;
		} else {
			return comparee.firstName.equals(firstName)&&
					comparee.lastName.equals(lastName)&&
					comparee.login.equals(login);
		}
	}

}
