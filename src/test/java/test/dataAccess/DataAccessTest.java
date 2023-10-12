package test.dataAccess;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;

public class DataAccessTest {

    private DataAccess dataAccess;

    @Before
    public void setUp() {
        dataAccess = new DataAccess(true); // Inicializa con el modo de inicialización
        dataAccess.initializeDB(); // Inicializa la base de datos con datos de prueba
    }

    @Test
    public void testGetUsersMasGananciasWithNoUsers() {
        // Prueba cuando no hay usuarios en la base de datos.
        Vector<Object> result = dataAccess.getUsersMasGanancias();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsersMasGananciasWithUsersWithoutTransactions() {
        // Prueba cuando hay usuarios sin transacciones.
        User user1 = dataAccess.getUser("Carlos");
        User user2 = dataAccess.getUser("Juan");
        User user3 = dataAccess.getUser("Paco");

        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contenga valores nulos para los usuarios sin transacciones.
        assertTrue(result.contains("Carlos"));
        assertTrue(result.contains(0.0f));
        assertTrue(result.contains(0));
        assertTrue(result.contains("Juan"));
        assertTrue(result.contains(0.0f));
        assertTrue(result.contains(0));
        assertTrue(result.contains("Paco"));
        assertTrue(result.contains(0.0f));
        assertTrue(result.contains(0));
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
        // Prueba cuando hay usuarios con transacciones.
        User user1 = dataAccess.getUser("Carlos");
        User user2 = dataAccess.getUser("Juan");
        User user3 = dataAccess.getUser("Paco");

        // Agregar transacciones a los usuarios
        dataAccess.addDinero("Carlos", 100.0f, false);
        dataAccess.anadirApuesta("Carlos", 1, 50.0f, "Local");
        dataAccess.addDinero("Juan", 200.0f, false);
        dataAccess.anadirApuesta("Juan", 1, 150.0f, "Empate");
        dataAccess.anadirApuesta("Juan", 2, 50.0f, "1-2 goles");
        dataAccess.addDinero("Paco", 500.0f, false);
        dataAccess.anadirApuesta("Paco", 1, 10.0f, "Empate");
        dataAccess.anadirApuesta("Paco", 2, 50.0f, "0 goles");

        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contenga los usuarios con sus ganancias y apuestas.
        assertTrue(result.contains("Carlos"));
        assertTrue(result.contains(50.0f)); // (100 - 50)
        assertTrue(result.contains(1)); // 1 apuesta
        assertTrue(result.contains("Juan"));
        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
        assertTrue(result.contains(2)); // 2 apuestas
        assertTrue(result.contains("Paco"));
        assertTrue(result.contains(440.0f)); // (500 - 10 - 50)
        assertTrue(result.contains(2)); // 2 apuestas
    }
}
