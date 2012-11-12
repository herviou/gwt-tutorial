package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

import fr.dhu.gwt.tp.client.HistoryToken;
import fr.dhu.gwt.tp.client.IPlus;
import fr.dhu.gwt.tp.client.ServicesFactory;
import fr.dhu.gwt.tp.shared.model.Person;

/**
 * The breadcrumb display the connected user and also offers the possibility to log out
 * @author David.Herviou
 *
 */
public class BreadCrumb extends Composite implements LoginEvent.Handler, ValueChangeHandler<String>{

	
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
	Hyperlink logout;
	
	@UiField
	BreadCrumbMessages messages;
	
	/**
	 * Timer use for deconnection
	 */
	Timer timer ;

	/**
	 * Default constructor for the breadcrumb UI element
	 */
	public BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
		
		// register for LoginEvent.Event that are fired on application event bus
		IPlus.getEventbus().addHandler(LoginEvent.Event.TYPE, this);
		
		// register for History token changes
		History.addValueChangeHandler(this);
		
		timer = new Timer() {
			@Override
			public void run() {
				checkIsConnected();
			}
		};
	}

	/**
	 * Trigger when a {@link LoginEvent.Event} is fired on global event bus
	 */
	@Override
	public void onLogin(LoginEvent.Event event) {
		if (event.getPerson() != null && event.getAction() == LoginEvent.Event.Action.CONNECTION_SUCCESSFULLY ) {
			login(event.getPerson());
		}
	}


	/**
	 * log user out
	 */
	private void logout() {
		timer.cancel();
		connectedUser = null;
		username.setInnerText("");
		History.newItem(HistoryToken.TOK_LOGIN);
	}
	
	/**
	 * log user in
	 */
	private void login(Person connectedUser) {
		GWT.log(connectedUser.toString());

		if (connectedUser != null) {
			this.connectedUser = connectedUser;
			username.setInnerText(messages.connectedUser(connectedUser.getFirstName()));
		}
	}
	
	
	
	/**
	 * Check if the user is already connected or not
	 */
	private void checkIsConnected() {
		
		ServicesFactory.getLoginServices().isConnected(new AsyncCallback<Person>() {
			
			@Override
			public void onSuccess(Person result) {
				GWT.log("check is connected " +result.toString());
				login(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				logout();
			}
		});
	}

	
	/**
	 * Trigger when history token change
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if( event.getValue().startsWith("/mainspace")) {
			timer.scheduleRepeating(3000);
		}
	}
		
	
}
