package fr.dhu.gwt.tp.server.dao;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import fr.dhu.gwt.tp.shared.model.Person;

/**
 * DAO for accessnig Person
 * @author David.Herviou
 *
 */
public class PersonDAO {
	
	// the singleton instance
	static PersonDAO instance = new PersonDAO();
	
	// the framework of serialisation
	private Kryo kryo = new Kryo();
	
	/**
	 * no body have to instanciate this DAO
	 */
	private PersonDAO() {
		
	}
	
	/**
	 * Get the singleton instance of this DAO
	 * @return
	 */
	public static PersonDAO getInstance() {
		return instance;
	}
	
	/**
	 * Add a person into the database linking the person login and password
	 * @param p
	 * @param password
	 * @return true if insertion succeed
	 */
	public boolean addPerson(Person p, String password) {
		boolean result = false;
		Connection conn = JDBCHelper.getInstance().connection();
		try {
			PreparedStatement psInsert = conn
					.prepareStatement("insert into person values (?, ?, ?)");

			psInsert.setString(1, p.getLogin());
			psInsert.setString(2, password);
			
			Output os = new Output(new ByteArrayOutputStream());
			kryo.writeObject(os, p);
			psInsert.setBytes(3, os.getBuffer());
			os.close();

			psInsert.executeUpdate();
			psInsert.close();

			conn.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// nothing to do if rollback failed
				}
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch(SQLException e) {
					// nothing to do if connection close failed
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Search a person according to login
	 * @param login of user to search
	 * @return the person if exist; null otherwise
	 */
	public Person searchPerson(String login) {
		Connection conn = JDBCHelper.getInstance().connection();
		Person result = null;
	
		try {
			PreparedStatement psSearch = conn.prepareStatement("select login,data from person where login = ?");
			psSearch.setString(1, login);
			
			ResultSet rs = psSearch.executeQuery();
			if(rs.next()) {
				byte[] data = rs.getBytes(2);
				Input in = new Input(data);
				result = kryo.readObject(in, Person.class);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// nothing to do if rollback failed
				}
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch(SQLException e) {
					// nothing to do if connection closing failed
				}
			}
		}
		
		return result;
	}

	
	/**
	 * Check if login/password is a real combination
	 * @param login
	 * @param password
	 * @return true if login/password exist
	 */
	public boolean checkPassword(String login, String password) {
		Connection conn = JDBCHelper.getInstance().connection();
		boolean result = false;
	
		try {
			PreparedStatement psSearch = conn.prepareStatement("select login,password from person where login = ?");
			psSearch.setString(1, login);
			
			ResultSet rs = psSearch.executeQuery();
			if(rs.next()) {
				String storedPassword = rs.getString(2);
				result = storedPassword.equals(password);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// nothing to do if rollback failed
				}
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch(SQLException e) {
					// nothing to do if connection closing failed
				}
			}
		}
		
		return result;
	}
	
}
