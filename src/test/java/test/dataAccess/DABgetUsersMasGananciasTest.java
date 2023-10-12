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
        dataAccess.open(false);
        //dataAccess.initializeDB(); // Inicializa la base de datos con datos de prueba
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
        User user1 = dataAccess.getUser("x1");
        User user2 = dataAccess.getUser("x2");

        // Agregar transacciones a los usuarios
        dataAccess.addDinero("x1", 100.0f, false);
        dataAccess.anadirApuesta("x1", 1, 50.0f, "Local");
        dataAccess.addDinero("x2", 200.0f, false);
        dataAccess.anadirApuesta("x2", 1, 150.0f, "Empate");
        dataAccess.anadirApuesta("x2", 2, 50.0f, "1-2 goles");
        
        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contiene los usuarios y sus ganancias
        assertTrue(result.contains("x1"));
        assertTrue(result.contains(50.0f)); // (100 - 50)
        assertTrue(result.contains("x2"));
        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
    }
}

