package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;

public class DABPronosticosExist {
	private DataAccess dataAccess;
	ConfigXML c=ConfigXML.getInstance();
	@Before
    public void setUp() {
        dataAccess = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
        dataAccess.initializeDB();// Inicializa con el modo de inicialización
        dataAccess.open(true);
	}
	
	@After
    public void close() {
    	dataAccess.close();
    }
	
	@Test
	public void testPronosticosExistWithValidQuestion() {
		// Prueba con una pregunta existente
		assertTrue(dataAccess.pronosticosExist(1));
	}

	@Test
	public void testPronosticosExistWithInvalidQuestion() {
		// Prueba con una pregunta inexistente
		assertFalse(dataAccess.pronosticosExist(100)); // Número de pregunta inexistente
	}
	
}
