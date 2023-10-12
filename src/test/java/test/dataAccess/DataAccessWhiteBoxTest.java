package test.dataAccess;
import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;

public class DataAccessWhiteBoxTest {

    private DataAccess dataAccess;

    @Before
    public void setUp() {
        dataAccess = new DataAccess(true); // Inicializa con el modo de inicialización
        dataAccess.initializeDB(); // Inicializa la base de datos con datos de prueba
    }

    @Test
    public void testGetUsersMasGananciasWithNoUsers() {
        Vector<Object> result = dataAccess.getUsersMasGanancias();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
        // Crea usuarios de prueba
        User user1 = dataAccess.getUser("Carlos");
        User user2 = dataAccess.getUser("Juan");

        // Agregar transacciones a los usuarios
        dataAccess.addDinero("Carlos", 100.0f, false);
        dataAccess.anadirApuesta("Carlos", 1, 50.0f, "Local");
        dataAccess.addDinero("Juan", 200.0f, false);
        dataAccess.anadirApuesta("Juan", 1, 150.0f, "Empate");
        dataAccess.anadirApuesta("Juan", 2, 50.0f, "1-2 goles");

        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contiene los usuarios y sus ganancias
        assertTrue(result.contains("Carlos"));
        assertTrue(result.contains(50.0f)); // (100 - 50)
        assertTrue(result.contains("Juan"));
        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
    }

    @Test
    public void testGetUsersMasGananciasWithNegativeTransactions() {
        User user3 = dataAccess.getUser("Paco");
        // Agregar transacciones con valores negativos
        dataAccess.addDinero("Paco", 100.0f, false);
        dataAccess.anadirApuesta("Paco", 1, -50.0f, "Local");
        dataAccess.anadirApuesta("Paco", 2, -30.0f, "1-2 goles");

        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que el resultado contiene el usuario y que las transacciones negativas no afectan el total
        assertTrue(result.contains("Paco"));
        assertTrue(result.contains(100.0f)); // (100 - 50 + 30)
    }

    @Test
    public void testGetUsersMasGananciasWithMultipleUsersAndNoTransactions() {
        // Crear varios usuarios sin transacciones
        User user4 = dataAccess.getUser("Fran");
        User user5 = dataAccess.getUser("Fran2");
        User user6 = dataAccess.getUser("Fran3");

        Vector<Object> result = dataAccess.getUsersMasGanancias();

        // Verificar que los usuarios estén en el resultado con ganancias en 0
        assertTrue(result.contains("Fran"));
        assertTrue(result.contains(0.0f));
        assertTrue(result.contains("Fran2"));
        assertTrue(result.contains(0.0f));
        assertTrue(result.contains("Fran3"));
        assertTrue(result.contains(0.0f));
    }
}

