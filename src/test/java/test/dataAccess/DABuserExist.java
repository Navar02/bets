package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;

public class DABuserExist {
	private DataAccess dataAccess;
	@Before
    public void setUp() {
        dataAccess = new DataAccess(true); // Inicializa con el modo de inicialización
        dataAccess.open(true);
        dataAccess.initializeDB(); // Inicializa la base de datos con datos de prueba
    }
    @After
    public void close() {
    	dataAccess.close();
    }
	@Test
    public void testUserExistWithExistingUser() {
        User user1 = dataAccess.getUser("carlos");
        // Supongamos que "Carlos" ya existe en la base de datos
        boolean userExists = dataAccess.userExist("Carlos");
        assertTrue(userExists);
    }

    @Test
    public void testUserExistWithNonExistingUser() {
        DataAccess dataAccess = new DataAccess();
        // Supongamos que "UsuarioNoExistente" no existe en la base de datos
        boolean userExists = dataAccess.userExist("UsuarioNoExistente");
        assertFalse(userExists);
    }
    
    @Test
    public void testUserExistWitNullUser() {
		try {
			DataAccess dataAccess = new DataAccess();
			// Supongamos que "UsuarioNoExistente" no existe en la base de datos
			boolean userExists = dataAccess.userExist(null);
			fail("no deberia llegar aqui");
		} catch (Exception e) {
			assertTrue(true);
		}
    }
    
}
