package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dataAccess.DataAccess;

public class DAWuserExistTest {
	@Test
    public void testUserExistWithExistingUser() {
        DataAccess dataAccess = new DataAccess();
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
}
