package fr.dhu.gwt.tp.server.dao;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import fr.dhu.gwt.tp.shared.model.Person;

public class PersonDAOTest {

		@Before
		public void setUp() {
			JDBCHelper.setDatabase("IPlusDBtest");
		}
		
		@Test
		public void testAll() {
			
			String login = "david.herviou@gmail.com-test";
			Person p = new Person();
			p.setLogin(login);
			p.setFirstName("david");
			p.setLastName("herviou");
			
			PersonDAO.getInstance().addPerson(p,"pa55w0rd");
			
			Person searched = PersonDAO.getInstance().searchPerson(login);
			
			System.err.println(p.getLogin()+" "+searched.getLogin());
			Assert.assertEquals(p, searched);
			
			
			Assert.assertTrue(PersonDAO.getInstance().checkPassword(login, "pa55w0rd"));
			Assert.assertFalse(PersonDAO.getInstance().checkPassword(login, "pa55w0rD"));
		}	
}
