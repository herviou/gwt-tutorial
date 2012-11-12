package fr.dhu.gwt.tp.client.widget;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import fr.dhu.gwt.tp.shared.model.Person;

/**
 * Event associated with Login widget As a convenience the Event class and
 * Handler interface are declared inside the same class
 * 
 * @author David.Herviou
 * 
 */
public class LoginEvent {

	/**
	 * The event associated with the login
	 * 
	 * @author David.Herviou
	 * 
	 */
	public static class Event extends GwtEvent<Handler> {
		/** TYPE of this event */
		public static final Type<LoginEvent.Handler> TYPE = new Type<LoginEvent.Handler>();

		enum Action {
			CONNECTION_SUCCESSFULLY, LOGIN_PASSWORD_FAILED,
		}

		Action action = Action.LOGIN_PASSWORD_FAILED;
		Object origin = null;
		Person person = null;
		
		/**
		 * @param origin
		 *            the object that fire the event
		 */
		public Event(Object origin, Action action) {
			this.origin = origin;
			this.action = action;
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
			return TYPE;
		}

		/**
		 * dispatcher to handler wrapper
		 */
		@Override
		protected void dispatch(Handler handler) {
			handler.onLogin(this);
		}

		/**
		 * get the associated action to this event
		 * @return return the enum value corresponding the action associated to this event
		 */
		public Action getAction() {
			return action;
		}

		/**
		 * 
		 * @return the object that is at the origin of this event
		 */
		public Object getOrigin() {
			return origin;
		}

		/**
		 * Set the person associated to this event
		 * @param person the person to set into the event
		 */
		public void setConnectedPerson(Person person) {
			this.person = person;
		}
		
		/**
		 * Get the person associated to this event
		 * @return the person if exist null otherwise
		 */
		public Person getPerson() {
			return person;
		}
	}

	/**
	 * The handler other object need to implements for being notified on
	 * {@link LoginEvent.Event}
	 * 
	 * @author David.Herviou
	 */
	public static interface Handler extends EventHandler {
		public void onLogin(LoginEvent.Event event);
	}

}
