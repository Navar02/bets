package test.dataAccess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Pronosticos;

public class DABPronosticosExistTest {
	
	static DataAccess sut;
	ConfigXML c=ConfigXML.getInstance();
	static TestDataAccess testDA;
	
	
	@Test
	public void testPronosticosExistWithValidQuestion() {
		// Prueba con una pregunta existente
		testDA = new TestDataAccess();
		sut = new DataAccess();
		Vector<String> vec=new Vector<String>();
		vec.add("Local"); vec.add("Empate"); vec.add("Visitante");
		Vector<Float> porc=new Vector<Float>();
		porc.add(Float.parseFloat("0.5")); porc.add(Float.parseFloat("0.2")); porc.add(Float.parseFloat("0.3"));
		domain.Event e=new domain.Event(33, "Real Madrid-Real Sociedad", UtilDate.newDate(2023,9+1,28));
		Pronosticos p=new Pronosticos(33, vec, porc);
		try {
			//e=sut.createEvent(33, "Real Madrid-Real Sociedad", UtilDate.newDate(2023,9+1,28));
			sut.createEvent(e.getEventNumber(), e.getDescription(), e.getEventDate());
			sut.createQuestion(e,"¿Quien gana?", Float.parseFloat("0.2"));
//    		sut.anadirPronostico(33, vec, porc);
			sut.anadirPronostico(33, p.getOpciones(), p.getPorcentajes());
    		assertTrue(sut.pronosticosExist(33));
    	}catch(Exception z) {
    		fail("No funciona correctamente");
    	}finally {
    		testDA.open();
    		testDA.eliminarPronostico(33);
    		testDA.removeEvent(e);
    		testDA.close();
    	}
	}

	@Test
	public void testPronosticosExistWithInvalidQuestion() {
		// Prueba con una pregunta inexistente
		
		testDA = new TestDataAccess();
		sut = new DataAccess();
		try {
    		assertTrue(sut.pronosticosExist(100)); // Número de pregunta inexistente
    	}catch(Exception e) {
    		fail("No funciona correctamente");
    	}finally {
    		testDA.open();
    		testDA.close();
    	}
	}
	
}