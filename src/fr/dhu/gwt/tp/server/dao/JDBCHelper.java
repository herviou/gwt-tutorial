package fr.dhu.gwt.tp.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Database access helper (very simple, ie no connection pooling!)
 * @author David.Herviou
 *
 */
public class JDBCHelper {

	// the default framework is embedded 
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private static String protocol = "jdbc:derby:";

	// package private visibility for test;
	static String database = "IPlusDB";
	static JDBCHelper instance = null;
	static AtomicBoolean started = new AtomicBoolean(false);

	/**
	 * no body have to instanciate this class
	 */
	private JDBCHelper() {
	}

	public static JDBCHelper getInstance() {
		if (instance == null) {
			loadDriver();
			instance = new JDBCHelper();
			instance.start();
		}
		return instance;
	}

	public static void setDatabase(String dbName) {
		database = dbName;
	}

	/**
	 * Loads the appropriate JDBC driver for this environment/framework. For
	 * example, if we are in an embedded environment, we load Derby's embedded
	 * Driver, <code>org.apache.derby.jdbc.EmbeddedDriver</code>.
	 */
	private static synchronized void loadDriver() {
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + driver);
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver "
					+ driver);
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver "
					+ driver);
			iae.printStackTrace(System.err);
		}
	}

	/**
	 * create the needed table on started if they have to.
	 */
	private synchronized void start() {
		if(started.get()) {
			return ;
		}
		
		Connection conn = null;
		Statement s = null;
		try {

			conn = connection();

			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.
			conn.setAutoCommit(false);

			s = conn.createStatement();

			s.execute("CREATE TABLE person(login varchar(255) NOT NULL," +
					" password varchar(255) NOT NULL," +
					" data blob," +
					" primary key(login))");

			s.close();
			conn.commit();
			started = new AtomicBoolean(true);
		} catch (SQLException se) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				//here we are really bad
			}
			System.err.println("table is already created");
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException sqle) {
				//here again we are really bad
			}
		}
	}

	/**
	 * stop the database. This has only to be called in a JDBCHelper lifecycle management
	 */
	public synchronized void stop() {
		try {
			// the shutdown=true attribute shuts down Derby
			DriverManager.getConnection("jdbc:derby:;shutdown=true");

			// To shut down a specific database only, but keep the
			// engine running (for example for connecting to other
			// databases), specify a database in the connection URL:
			// DriverManager.getConnection("jdbc:derby:" + dbName +
			// ";shutdown=true");
		} catch (SQLException se) {
			if (((se.getErrorCode() == 50000) && ("XJ015".equals(se
					.getSQLState())))) {
				// we got the expected exception
				System.out.println("Derby shut down normally");
				// Note that for single database shutdown, the expected
				// SQL state is "08006", and the error code is 45000.
			} else {
				// if the error code or SQLState is different, we have
				// an unexpected exception (shutdown failed)
				System.err.println("Derby did not shut down normally");
				se.printStackTrace();
			}
		}
	}

	public Connection connection() {
		Connection conn = null;
		try {
			Properties props = new Properties(); // connection properties
			// providing a user name and password is optional in the embedded
			// and derbyclient frameworks
			props.put("user", database);
			props.put("password", database);

			conn = DriverManager.getConnection(protocol + database
					+ ";create=true", props);
			conn.setAutoCommit(false);
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
		}

		return conn;
	}

}
