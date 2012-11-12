package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import fr.dhu.gwt.tp.client.HistoryToken;
import fr.dhu.gwt.tp.client.ServicesFactory;
import fr.dhu.gwt.tp.shared.model.Person;

/**
 * Widget for login in
 * @author David.Herviou
 *
 */
public class Login extends Composite implements ValueChangeHandler<String> {

	/**
	 * UIBinder generator
	 */
	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
	
	/**
	 * Tag interface for UIBinder. Default template file is : Login.ui.xml
	 */
	interface LoginUiBinder extends UiBinder<Widget, Login> {}

	/**
	 * Internationalization of messages
	 * Instantiated with UIBinder
	 */
	@UiField LoginMessages messages;

	//UiField with which we interact
	@UiField TextBox email;
	@UiField PasswordTextBox password;
	@UiField Button logIn;
	
	/**
	 * Build the widget and initiaze all its connection to events
	 */
	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		History.addValueChangeHandler(this);
	}

	/**
	 * This method is triggered each time the History token has changed
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

		// check if token is interresting for me
		if(event.getValue().startsWith("/login/")) {
			
			// get the email if provided
			//
			RegExp exp = RegExp.compile("/login/(.+)");
			MatchResult result = exp.exec(event.getValue());
			
			if(result!=null){
				String tokEmail = result.getGroup(1);
				email.setText(tokEmail);
			} else{
				// restore default 
				email.setText("your@email.here");
			}
		}
	}

	
	/**
	 * Trigger when login button is pressed
	 * Here we check call backend to connect user
	 */
	@UiHandler("logIn")
	public void logMeIn(ClickEvent evoent) {
		ServicesFactory.getLoginServices().connect(
					email.getText(), 
					password.getText(),
					new AsyncCallback<Person>() {
						
						@Override
						public void onSuccess(Person result) {
							if(result != null) {
								History.newItem(HistoryToken.TOK_MAIN);
							} else {
								Window.alert(messages.loginPasswordFailure());
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(messages.loginPasswordFailure());
							
						}
					});
	}
	
	
}
