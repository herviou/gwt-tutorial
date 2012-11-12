package fr.dhu.gwt.tp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		Person p = new Person();
		p.setFirstName("user");
		p.setLastName("tester");
		p.setLogin("user.tester2@iplus.plus");
		String password = "testerpassword";
		
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
		
		
//		ServicesFactory.getLoginServices().isConnected(new AsyncCallback<Boolean>() {
//			@Override
//			public void onFailure(Throwable caught) {
//				GWT.log("not logged in");
//			}
//		
//			@Override
//			public void onSuccess(Boolean result) {
//				GWT.log("already login");
//			}
//		});
		
		
		new AppController();
		
		History.fireCurrentHistoryState();
	}
}
