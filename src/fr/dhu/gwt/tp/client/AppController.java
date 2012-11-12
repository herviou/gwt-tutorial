package fr.dhu.gwt.tp.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * This class manage visibility of Widget
 * @author David.Herviou
 *
 */
public class AppController implements ValueChangeHandler<String> {

	/**
	 * Managment of widget are done inside a Main container
	 * wich is simply a FlowPanel aka a <div>
	 */
	FlowPanel flowPanel;
	
	/**
	 * login widget displayed on {@link HistoryToken#TOK_LOGIN}
	 */
	Widget login ;
	
	/**
	 * BreadCrumb widget displayed on {@link HistoryToken#TOK_MAINSPACE}
	 */
	Widget breadCrumb;
	
	/**
	 * Build the Application Controller
	 * This lead to the creation of the widget it manages
	 */
	public AppController() {
		
		// Build the widgets and container
		//
		flowPanel = new FlowPanel();
		login = WidgetsFactory.loginWidget();
		breadCrumb = WidgetsFactory.breadCrumbWidget();
		
		// this is the only time we have to call RootPanel.get()
		RootPanel.get().add(flowPanel);
		
		//  Register AppContoller to be notified on History Token changes
		History.addValueChangeHandler(this);
	}
	
	/**
	 * Manage History Token
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if(event.getValue().startsWith("/login/")) {
			flowPanel.clear();
			flowPanel.add(login);
		} else if (event.getValue().startsWith("/mainspace")) {
			flowPanel.clear();
			flowPanel.add(breadCrumb);
		} else {
			flowPanel.clear();
		}
		
		
	}
	
		
}
