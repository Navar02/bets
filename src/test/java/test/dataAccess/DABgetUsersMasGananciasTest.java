package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;

public class DABgetUsersMasGananciasTest {

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
    public void testGetUsersMasGananciasWithNoUsers() {
        Vector<Object> result = dataAccess.getUsersMasGanancias();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
        User user1 = dataAccess.getUser("Paco");
        User user2 = dataAccess.getUser("juan");
        
        // Agregar transacciones a los usuarios
        dataAccess.addDinero("Paco", 100.0f, false);
        dataAccess.anadirApuesta("Paco", 1, 50.0f, "Local");
        dataAccess.addDinero("Juan", 200.0f, false);
        dataAccess.anadirApuesta("Juan", 1, 150.0f, "Empate");
        dataAccess.anadirApuesta("Juan", 2, 50.0f, "1-2 goles");
        
        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contiene los usuarios y sus ganancias
        assertTrue(result.contains("Paco"));
        assertTrue(result.contains(50.0f)); // (100 - 50)
        assertTrue(result.contains("Juan"));
        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
    }
}

