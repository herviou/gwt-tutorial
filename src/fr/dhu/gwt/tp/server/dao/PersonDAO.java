package fr.dhu.gwt.tp.server.dao;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import fr.dhu.gwt.tp.shared.model.Person;

public class PersonDAO {
	
	static PersonDAO instance = new PersonDAO();
	private Kryo kryo = new Kryo();
	
	private PersonDAO() {
		
	}
	
	public static PersonDAO getInstance() {
		return instance;
	}
	
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

			System.out.println("Inserted " + p.getLogin());

			conn.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	
	
	public Person searchPerson(String login) {
		Connection conn = JDBCHelper.getInstance().connection();
		Person p = null; 
		Person result = null;
	
		try {
			PreparedStatement psSearch = conn.prepareStatement("select login,password,data from person where login = ?");
			psSearch.setString(1, login);
			
			ResultSet rs = psSearch.executeQuery();
			if(rs.next()) {
				byte[] data = rs.getBytes(3);
				Input in = new Input(data);
				result = kryo.readObject(in, Person.class);
				System.err.println(" resultat"+ result);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
}
