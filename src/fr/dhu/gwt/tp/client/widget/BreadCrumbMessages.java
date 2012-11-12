package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.i18n.client.LocalizableResource.DefaultLocale;
import com.google.gwt.i18n.client.Messages;

@DefaultLocale("fr")
public interface BreadCrumbMessages extends Messages {

	@DefaultMessage("Bienvenue sur IPlus+ {0}")
	String connectedUser(String username);

	@DefaultMessage("Deconnexion")
	String logout();

}