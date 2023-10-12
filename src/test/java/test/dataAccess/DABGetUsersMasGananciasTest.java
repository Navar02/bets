package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Tarjeta;
import domain.User;

public class DABGetUsersMasGananciasTest {

    private DataAccess dataAccess;
    ConfigXML c = ConfigXML.getInstance();

    @Before
    public void setUp() {
        dataAccess = new DataAccess(true); // Always initialize the database
        dataAccess.open(true);
         // Initialize the database with test data
		dataAccess.anadirUsuario("Paco", "123", "1111111111111113", "jaheki1761@angeleslid.com");
		dataAccess.anadirUsuario("Juan", "123", "1111111111111112", "jaheki1761@angeleslid.com");
		
    }

    @After
    public void close() {
        dataAccess.close();
    }

    @Test
    public void testGetUsersMasGananciasWithNoUsers() {
        Vector<Object> result = dataAccess.getUsersMasGanancias();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
        User user1 = dataAccess.getUser("Paco");
        User user2 = dataAccess.getUser("Juan");
        
        // Add transactions to the users
        dataAccess.addDinero("Paco", 100.0f, false);
        dataAccess.anadirApuesta("Paco", 3, 50.0f, "Empate");
        dataAccess.addDinero("Juan", 200.0f, false);
        dataAccess.anadirApuesta("Juan", 3, 150.0f, "Empate");
        dataAccess.anadirApuesta("Juan", 4, 50.0f, "1-2 goles");
        
        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verify that the result contains the users and their earnings
        assertTrue(result.contains("Paco"));
        assertTrue(result.contains(50.0f)); // (100 - 50)
        assertTrue(result.contains("Juan"));
        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
    }
}
