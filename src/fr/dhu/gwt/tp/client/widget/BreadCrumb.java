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
public class BreadCrumb extends Composite {

	
	// ui binder tag interface
	//
	interface BreadCrumbUiBinder extends UiBinder<Widget, BreadCrumb> {
	}
	
	// the ui binder builder
	private static BreadCrumbUiBinder uiBinder = GWT.create(BreadCrumbUiBinder.class);

	/**
	 * Default constructor for the breadcrumb UI element
	 */
	public BreadCrumb() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
