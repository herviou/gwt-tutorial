package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.dhu.gwt.tp.client.HistoryToken;
import fr.dhu.gwt.tp.client.IPlus;
import fr.dhu.gwt.tp.shared.model.Person;

/**
 * The breadcrumb display the connected user and also offers the possibility to log out
 * @author David.Herviou
 *
 */
public class BreadCrumb extends Composite implements LoginEvent.Handler {

	
	// ui binder tag interface
	//
	interface BreadCrumbUiBinder extends UiBinder<Widget, BreadCrumb> {
	}
	
	// the ui binder builder
	private static BreadCrumbUiBinder uiBinder = GWT.create(BreadCrumbUiBinder.class);

	
	// maintain the lastest person connected
	Person connectedUser ;
	
	
	@UiField
	DivElement username;
	
	@UiField
	Label logout;
	
	@UiField
	BreadCrumbMessages messages;

	/**
	 * Default constructor for the breadcrumb UI element
	 */
	public BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// register for LoginEvent.Event that are fired on application event bus
		IPlus.getEventbus().addHandler(LoginEvent.Event.TYPE, this);
	}

	/**
	 * Trigger when a {@link LoginEvent.Event} is fired on global event bus
	 */
	@Override
	public void onLogin(LoginEvent.Event event) {
		Person connectedUser = event.getPerson();
		GWT.log(connectedUser.toString());
		if (event.getPerson() != null && event.getAction() == LoginEvent.Event.Action.CONNECTION_SUCCESSFULLY ) {
			this.connectedUser = connectedUser;
			username.setInnerText(messages.connectedUser(connectedUser.getFirstName()));
		}
	}


	/**
	 * log user out when click on button logout
	 * @param event
	 */
	@UiHandler({"logout"})
	public void logout(ClickEvent event) {
		connectedUser = null;
		username.setInnerText("");
		History.newItem(HistoryToken.TOK_LOGIN);
	}
	
}
