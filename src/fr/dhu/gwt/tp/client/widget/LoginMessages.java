package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

@DefaultLocale("fr")
public interface LoginMessages extends Messages {

	@DefaultMessage("login")
	String login();

	@DefaultMessage("mot de passe")
	String password();

	@DefaultMessage("vote@mail.ici")
	String loginDefaultText();

	@DefaultMessage("password")
	String passwordDefaultText();
	
	@DefaultMessage("pas de compte, allez-y!")
	String noAccount();
	
	@DefaultMessage("Connection")
	String logMeIn();
	
	@DefaultMessage("Login/Mot de passe non valide")
	String loginPasswordFailure();
}