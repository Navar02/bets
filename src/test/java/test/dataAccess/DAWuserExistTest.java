package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.User;

public class DAWuserExistTest {
	 static DataAccess sut;
	 ConfigXML c = ConfigXML.getInstance();
	 static TestDataAccess testDA;
	@Test
    public void testUserExistWithExistingUser() {
		testDA = new TestDataAccess();
		sut = new DataAccess();
		User u2=new User("Pepe", "123", "1111111111111333", "mi@correo.com");
		try {
		String u1= sut.anadirUsuario("Pepe", "123", "1111111111111333", "mi@correo.com");
		}catch(Exception e) {
			fail("error");
		}finally {
			testDA.open();
			testDA.eliminarUser(u2);
			testDA.close();
		}
        // Supongamos que "Carlos" ya existe en la base de datos
        boolean userExists = sut.userExist("Pepe");
        assertTrue(userExists);
    }

    @Test
    public void testUserExistWithNonExistingUser() {
        // Supongamos que "UsuarioNoExistente" no existe en la base de datos
    	testDA = new TestDataAccess();
		sut = new DataAccess();
		try {
			boolean userExists = sut.userExist("UsuarioNoExistente");
			assertFalse(userExists);
		}catch(Exception e) {
			fail("castania");
		}
    }
}
