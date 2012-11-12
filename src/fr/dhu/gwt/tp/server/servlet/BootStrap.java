package fr.dhu.gwt.tp.server.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import fr.dhu.gwt.tp.server.dao.JDBCHelper;

/**
 * Startup bootstraper
 * @author David.Herviou
 *
 */
public class BootStrap extends HttpServlet {

	private static final long serialVersionUID = -2275685925915662237L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		//force initialization of Databases connection
		JDBCHelper.getInstance();
	}
	
	@Override
	public void destroy() {
		//on server shutdown : shutdown database
		JDBCHelper.getInstance().stop();
	}
}
