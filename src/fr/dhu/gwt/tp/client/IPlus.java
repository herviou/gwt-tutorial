package fr.dhu.gwt.tp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.dhu.gwt.tp.shared.model.Person;
import fr.dhu.gwt.tp.shared.services.LoginServicesException;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IPlus implements EntryPoint {

	/**
	 * The singleton event bus of the application
	 */
	private static final EventBus eventBus = new SimpleEventBus();

	public static EventBus getEventbus() {
		return eventBus;
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		Person p = new Person();
		p.setFirstName("David");
		p.setLastName("Herviou");
		p.setLogin("david.herviou@iplus.plus");
		String password = "dhpassword";
		
		ServicesFactory.getLoginServices().register(p, password, new AsyncCallback<Person>() {
			@Override
			public void onSuccess(Person result) {
				GWT.log(result.getFirstName()+" "+result.getLastName());
				Window.alert("OK : creation of "+result+" succeed");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				if(caught instanceof LoginServicesException) {
					Window.alert("fail to register (error code "+((LoginServicesException)caught).getCode()+")");
				} else {
					GWT.log("fail to register error code unknown");
				}
			}
		});
		
		// create the application controller and start application UI
		//
		new AppController();
		
		History.fireCurrentHistoryState();
	}
}
