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

    static DataAccess sut;
    ConfigXML c = ConfigXML.getInstance();
    static TestDataAccess testDA;

//    @Before
//    public void setUp() {
//        sut = new sut(); // Always initialize the database
////        sut.open(false);
//         // Initialize the database with test data
//        sut.close();
//    }

//    @After
//    public void close() {
//        sut.close();
//    }

    @Test
    public void testGetUsersMasGananciasWithNoUsers() {
    	try {
    		sut = new DataAccess();
    		Vector<Object> result = sut.getUsersMasGanancias();
    		assertTrue(result.isEmpty());
    	}catch(Exception e) {
    		fail("Va mal");
    	}
    }

    @Test
    public void testGetUsersMasGananciasWithUsersAndTransactions() {
    	testDA = new TestDataAccess();
		sut = new DataAccess();
		String u1= sut.anadirUsuario("Paco22", "123", "1111111111111122", "mi@correo.com");
		User uu1 = sut.getUser(u1);
    	try {
            float dinero1= uu1.getTarjeta().getDinero();
            System.out.println("Dinero de Paco despues de apostar: " + dinero1);
            // Add transactions to the users
            sut.setResult(4, "Empate");
            Vector<Object> result = sut.getUsersMasGanancias();
            
    	}catch(Exception e) {
    		fail("Mal");
    	}finally {
    		testDA.open();
    		testDA.eliminarUser(uu1);
    		testDA.close();
    	}
        
//        for(Object o : result) {
//        }

        // Verify that the result contains the users and their earnings
//        assertTrue(result.contains("Paco"));
//        assertTrue(result.contains(50.0f)); // (100 - 50)
//        assertTrue(result.contains("Juan"));
//        assertTrue(result.contains(100.0f)); // (200 - 150 + 50)
    }
}