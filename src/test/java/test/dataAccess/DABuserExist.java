package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.User;

public class DABuserExist {
	private DataAccess sut;
	static TestDataAccess testDA;
	@Test
    public void testUserExistWithExistingUser() {
        sut = new DataAccess();
        testDA = new TestDataAccess();
        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
        User u = sut.getUser("Paco22");
        try {
        	assertTrue(sut.userExist(u.getUserName()));
        }catch(Exception e) {
        	fail("No funciona bien");
        } finally {
        	testDA.open();
        	testDA.eliminarUser(u);
        	testDA.close();
        }
    }

    @Test
    public void testUserExistWithNonExistingUser() {
    	sut = new DataAccess();
        testDA = new TestDataAccess();
        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
        User u = sut.getUser("Paco21");
        User u2 = sut.getUser("Paco22");
        try {
        	assertFalse(sut.userExist("Paco21"));
        	
        }catch(Exception e) {
        	fail("Mal, Paco21 no deberia de existir");
        } finally {
        	testDA.open();
        	testDA.eliminarUser(u2);
        	testDA.close();
        }
    }
    
    @Test
    public void testUserExistWitNullUser() {
    	sut = new DataAccess();
        testDA = new TestDataAccess();
        sut.anadirUsuario("Paco22", "123", "1111111111111122", "tu@email.com");
        User u = sut.getUser("Paco21");
        User u2 = sut.getUser("Paco22");
        try {
        	assertTrue(sut.userExist(u.getUserName()));
        	
        }catch(Exception e) {
        	assertTrue(true);
        } finally {
        	testDA.open();
        	testDA.eliminarUser(u2);
        	testDA.close();
        }
    }
    
}
