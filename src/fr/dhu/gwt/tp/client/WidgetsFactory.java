package fr.dhu.gwt.tp.client;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.dhu.gwt.tp.client.widget.Login;

/**
 * Factory of Widgets
 * @author David.Herviou
 *
 */
public class WidgetsFactory {

	/**
	 * create a new Login Widget
	 * @return the Login Widget
	 */
	public static Widget loginWidget() {
		return new Login();
	}
	
	/**
	 * create a new BreadCrumb Widget
	 * @return the Login Widget
	 */
	public static Widget breadCrumbWidget() {
		return new HTMLPanel("here the main space");
	}
	
}
